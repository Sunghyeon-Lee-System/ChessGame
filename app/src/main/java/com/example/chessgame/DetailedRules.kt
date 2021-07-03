package com.example.chessgame

import com.example.chessgame.pieces.*
import com.example.chessgame.shareddata.MovementOfKingAndRook1Device
import com.example.chessgame.shareddata.MovementOfKingAndRook2Device

class DetailedRules(val board: Array<Array<Piece>>) {
    var pPieceWhichCheck = Position(-1, -1)

    private fun isCanCastling(): HashSet<String> {

        val state = HashSet<String>()
        if (!MovementOfKingAndRook1Device.isWhiteKingMoved && !MovementOfKingAndRook1Device.isWhiteRightRookMoved
            && board[7][5] is Empty && board[7][6] is Empty
        ) {
            MovementOfKingAndRook1Device.whiteKSC = true
            MovementOfKingAndRook2Device.whiteKSC = true
            state.add("WhiteKSC")
        }
        if (!MovementOfKingAndRook1Device.isWhiteKingMoved && !MovementOfKingAndRook1Device.isWhiteLeftRookMoved
            && board[7][1] is Empty && board[7][2] is Empty && board[7][3] is Empty
        ) {
            MovementOfKingAndRook1Device.whiteQSC = true
            MovementOfKingAndRook2Device.whiteQSC = true
            state.add("WhiteQSC")
        }
        if (!MovementOfKingAndRook1Device.isBlackKingMoved && !MovementOfKingAndRook1Device.isBlackRightRookMoved
            && board[0][5] is Empty && board[0][6] is Empty
        ) {
            MovementOfKingAndRook1Device.blackKSC = true
            MovementOfKingAndRook2Device.blackKSC = true
            state.add("BlackKSC")
        }
        if (!MovementOfKingAndRook1Device.isBlackKingMoved && !MovementOfKingAndRook1Device.isBlackLeftRookMoved
            && board[0][1] is Empty && board[0][2] is Empty && board[0][3] is Empty
        ) {
            MovementOfKingAndRook1Device.blackQSC = true
            MovementOfKingAndRook2Device.blackQSC = true
            state.add("BlackQSC")
        }

        return state
    }

    fun castlingManager(): HashSet<Position>? {
        val positionSet = HashSet<Position>()

        val state = isCanCastling()
        val iterator = state.iterator()

        while (iterator.hasNext()) {
            when (iterator.next()) {
                "WhiteKSC" -> positionSet.add(Position(7, 6))
                "WhiteQSC" -> positionSet.add(Position(7, 2))
                "BlackKSC" -> positionSet.add(Position(0, 6))
                "BlackQSC" -> positionSet.add(Position(0, 2))
            }
        }
        return if (positionSet.isEmpty()) {
            null
        } else {
            positionSet
        }
    }

    private fun isInBoard(x: Int, y: Int): Boolean {
        if (x in 0..7 && y in 0..7) {
            return true
        }
        return false
    }

    fun enpassantManager(): HashSet<Pair<Position, Position>> {
        val state = HashSet<Pair<Position, Position>>()

        for (y in 0..7) {
            if (board[6][y] is Pawn) {
                val color = (board[6][y] as Pawn).colorId
                if (color) {
                    if (y >= 1) {
                        if (board[4][y - 1] is Pawn) {
                            val color = (board[4][y - 1] as Pawn).colorId
                            if (!color) {
                                state.add(Pair(Position(6, y), Position(4, y - 1)))
                            }
                        }
                    }
                    if (y <= 6) {
                        if (board[4][y + 1] is Pawn) {
                            val color = (board[4][y + 1] as Pawn).colorId
                            if (!color) {
                                state.add(Pair(Position(6, y), Position(4, y + 1)))
                            }
                        }
                    }
                }
            }
            if (board[1][y] is Pawn) {
                val color = (board[1][y] as Pawn).colorId
                if (!color) {
                    if (y >= 1) {
                        if (board[3][y - 1] is Pawn) {
                            val color = (board[3][y - 1] as Pawn).colorId
                            if (color) {
                                state.add(Pair(Position(1, y), Position(3, y - 1)))
                            }
                        }
                    }
                    if (y <= 6) {
                        if (board[3][y + 1] is Pawn) {
                            val color = (board[3][y + 1] as Pawn).colorId
                            if (color) {
                                state.add(Pair(Position(1, y), Position(3, y + 1)))
                            }
                        }
                    }
                }
            }
        }
        return state
    }

    fun isKingInDanger(
        x: Int,
        y: Int,
        clickedTileType: String,
        clickedTileColor: Boolean
    ): Boolean {
        val canEatPosition = when (clickedTileType) {
            "Pawn" -> {
                (board[x][y] as Pawn).isCanEat(Position(x, y), board, clickedTileColor)
            }
            "Rook" -> {
                (board[x][y] as Rook).isCanEat(Position(x, y), board, clickedTileColor)
            }
            "Knight" -> {
                (board[x][y] as Knight).isCanEat(Position(x, y), board, clickedTileColor)
            }
            "Bishop" -> {
                (board[x][y] as Bishop).isCanEat(Position(x, y), board, clickedTileColor)
            }
            "Queen" -> {
                (board[x][y] as Queen).isCanEat(Position(x, y), board, clickedTileColor)
            }
            "King" -> {
                (board[x][y] as King).isCanEat(Position(x, y), board, clickedTileColor)
            }
            else -> HashSet()
        }
        pPieceWhichCheck = Position(x, y)

        val iterator = canEatPosition.iterator()

        for (i in 0..7) {
            for (j in 0..7) {
                if (board[i][j] is King) {
                    if ((board[i][j] as King).colorId != clickedTileColor) {
                        val kingPosition = Position(i, j)

                        while (iterator.hasNext()) {
                            val position = iterator.next()
                            if (position == kingPosition) {
                                return true
                            }
                        }
                        break
                    }
                }
            }
        }
        return false
    }

    private fun getKingPosition(color: Boolean): Position {
        for (i in 0..7) {
            for (j in 0..7) {
                if (board[i][j] is King) {
                    if ((board[i][j] as King).colorId == color) {
                        return Position(i, j)
                    }
                }
            }
        }
        return Position(-1, -1)
    }

    fun isCheck(x: Int, y: Int, color: Boolean): Boolean {
        val canEatSet = HashSet<HashSet<Position>>()

        for (i in 0..7) {
            for (j in 0..7) {
                when (board[i][j]) {
                    is Pawn -> canEatSet.add(
                        (board[i][j] as Pawn).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                    )
                    is Rook -> canEatSet.add(
                        (board[i][j] as Rook).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                    )
                    is Knight -> canEatSet.add(
                        (board[i][j] as Knight).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                    )
                    is Bishop -> canEatSet.add(
                        (board[i][j] as Bishop).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                    )
                    is Queen -> canEatSet.add(
                        (board[i][j] as Queen).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                    )
                    is King -> {
                    }
                }
            }
        }

        val bigIterator = canEatSet.iterator()
        while (bigIterator.hasNext()) {
            val smallIterator = bigIterator.next().iterator()
            while (smallIterator.hasNext()) {
                if (smallIterator.next() == Position(x, y)) {
                    return true
                }
            }
        }
        return false
    }

    fun isCheckMate(color: Boolean): Boolean {
        var aroundOfKingIsEmpty = false
        val kingPosition = getKingPosition(!color)
        val x = kingPosition.x
        val y = kingPosition.y

        for (i in -1..1) {
            for (j in -1..1) {
                if (isInBoard(x + i, y + j)) {
                    if (board[x + i][y + j] is Empty) {
                        aroundOfKingIsEmpty = true
                    }
                }
            }
        }

        if (aroundOfKingIsEmpty) {
            if (!isCheck(pPieceWhichCheck.x, pPieceWhichCheck.y, color)) {
                val canMoveSet = (board[x][y] as King).getCanMoveArea(kingPosition, board, color)
                if (canMoveSet.size == 1) {
                    return true
                }
            }
        }
        return false
    }
}