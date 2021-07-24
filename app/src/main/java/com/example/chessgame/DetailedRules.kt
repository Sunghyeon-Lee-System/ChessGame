package com.example.chessgame

import com.example.chessgame.pieces.*
import com.example.chessgame.shareddata.MovementOfKingAndRook1Device
import com.example.chessgame.shareddata.MovementOfKingAndRook2Device

class DetailedRules(val board: Array<Array<Piece>>) {
    private var pPieceWhichCheckPos = HashSet<Position>()
    private var pPieceWhichCheckKind = HashSet<String>()

    private fun isCanCastling(): HashSet<String> {

        val state = HashSet<String>()
        if (!MovementOfKingAndRook1Device.isWhiteKingMoved && !MovementOfKingAndRook1Device.isWhiteRightRookMoved
            && board[7][5] is Empty && board[7][6] is Empty
        ) {
            var canCastling = true
            for (y in 4..6) {
                if (isCheck(7, y, true)) {
                    canCastling = false
                }
            }
            if (canCastling) {
                MovementOfKingAndRook1Device.whiteKSC = true
                MovementOfKingAndRook2Device.whiteKSC = true
                state.add("WhiteKSC")
            }
        }
        if (!MovementOfKingAndRook1Device.isWhiteKingMoved && !MovementOfKingAndRook1Device.isWhiteLeftRookMoved
            && board[7][1] is Empty && board[7][2] is Empty && board[7][3] is Empty
        ) {
            var canCastling = true
            for (y in 1..4) {
                if (isCheck(7, y, true)) {
                    canCastling = false
                }
            }
            if (canCastling) {
                MovementOfKingAndRook1Device.whiteQSC = true
                MovementOfKingAndRook2Device.whiteQSC = true
                state.add("WhiteQSC")
            }
        }
        if (!MovementOfKingAndRook1Device.isBlackKingMoved && !MovementOfKingAndRook1Device.isBlackRightRookMoved
            && board[0][5] is Empty && board[0][6] is Empty
        ) {
            var canCastling = true
            for (y in 4..6) {
                if (isCheck(0, y, false)) {
                    canCastling = false
                }
            }
            if (canCastling) {
                MovementOfKingAndRook1Device.blackKSC = true
                MovementOfKingAndRook2Device.blackKSC = true
                state.add("BlackKSC")
            }
        }
        if (!MovementOfKingAndRook1Device.isBlackKingMoved && !MovementOfKingAndRook1Device.isBlackLeftRookMoved
            && board[0][1] is Empty && board[0][2] is Empty && board[0][3] is Empty
        ) {
            var cancastling = true
            for (y in 1..4) {
                if (isCheck(0, y, false)) {
                    cancastling = false
                }
            }
            if (cancastling) {
                MovementOfKingAndRook1Device.blackQSC = true
                MovementOfKingAndRook2Device.blackQSC = true
                state.add("BlackQSC")
            }
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

    fun getKingPosition(color: Boolean): Position {
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
        var isCheck = false

        val whiteKingPos = getKingPosition(true)
        val blackKingPos = getKingPosition(false)

        if (color) {
            board[whiteKingPos.x][whiteKingPos.y] = Empty()
        } else {
            board[blackKingPos.x][blackKingPos.y] = Empty()
        }

        for (i in 0..7) {
            for (j in 0..7) {
                when (board[i][j]) {
                    is Pawn -> {
                        val pawnColor = (board[i][j] as Pawn).colorId
                        val canPawnEatSet = HashSet<Position>()
                        if (pawnColor != color) {
                            if (pawnColor) {
                                if (i != 0) {
                                    if (j != 0) {
                                        canPawnEatSet.add(Position(i - 1, j - 1))
                                    }
                                    if (j != 7) {
                                        canPawnEatSet.add(Position(i - 1, j + 1))
                                    }
                                }
                            } else {
                                if (i != 7) {
                                    if (j != 0) {
                                        canPawnEatSet.add(Position(i + 1, j - 1))
                                    }
                                    if (j != 7) {
                                        canPawnEatSet.add(Position(i + 1, j + 1))
                                    }
                                }
                            }
                        }
                        val iterator = canPawnEatSet.iterator()
                        while (iterator.hasNext()) {
                            val canPawnEatPos = iterator.next()
                            if (canPawnEatPos == Position(x, y)) {
                                pPieceWhichCheckPos.add(Position(i, j))
                                pPieceWhichCheckKind.add("Pawn")
                                isCheck = true
                            }
                        }
                        canEatSet.add(canPawnEatSet)
                    }
                    is Rook -> {
                        val canRookEatSet = (board[i][j] as Rook).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                        val iterator = canRookEatSet.iterator()
                        while (iterator.hasNext()) {
                            val canRookEatPos = iterator.next()
                            if (canRookEatPos == Position(x, y)) {
                                pPieceWhichCheckPos.add(Position(i, j))
                                pPieceWhichCheckKind.add("Rook")
                                isCheck = true
                            }
                        }
                    }
                    is Knight -> {
                        val canKnightEatSet = (board[i][j] as Knight).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                        val iterator = canKnightEatSet.iterator()
                        while (iterator.hasNext()) {
                            val canKnightEatPos = iterator.next()
                            if (canKnightEatPos == Position(x, y)) {
                                pPieceWhichCheckPos.add(Position(i, j))
                                pPieceWhichCheckKind.add("Knight")
                                isCheck = true
                            }
                        }
                    }
                    is Bishop -> {
                        val canBishopEatSet = (board[i][j] as Bishop).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                        val iterator = canBishopEatSet.iterator()
                        while (iterator.hasNext()) {
                            val canBishopEatPos = iterator.next()
                            if (canBishopEatPos == Position(x, y)) {
                                pPieceWhichCheckPos.add(Position(i, j))
                                pPieceWhichCheckKind.add("Bishop")
                                isCheck = true
                            }
                        }
                    }
                    is Queen -> {
                        val canQueenEatSet = (board[i][j] as Queen).getCanMoveArea(
                            Position(i, j),
                            board,
                            !color
                        )
                        val iterator = canQueenEatSet.iterator()
                        while (iterator.hasNext()) {
                            val canQueenEatPos = iterator.next()
                            if (canQueenEatPos == Position(x, y)) {
                                pPieceWhichCheckPos.add(Position(i, j))
                                pPieceWhichCheckKind.add("Queen")
                                isCheck = true
                            }
                        }
                    }
                }
            }
        }

        if (color) {
            board[whiteKingPos.x][whiteKingPos.y] = King(true)
        } else {
            board[blackKingPos.x][blackKingPos.y] = King(false)
        }

        return isCheck
    }

    fun isCheckMate(color: Boolean): Boolean {
        var aroundOfKingIsEmpty = false
        val kingPosition = getKingPosition(!color)
        val x = kingPosition.x
        val y = kingPosition.y
        val canBlockPositions = HashSet<Position>()

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
            if (pPieceWhichCheckPos.size == 1) {
                val iterator = pPieceWhichCheckPos.iterator()
                val pPieceWhichCheckPosition = iterator.next()
                val iterator2 = pPieceWhichCheckKind.iterator()
                val pPieceWhichCheckKindStr = iterator2.next()

                if (!isCheck(pPieceWhichCheckPosition.x, pPieceWhichCheckPosition.y, color)) {
                    val canMoveSet =
                        (board[x][y] as King).getCanMoveArea(kingPosition, board, !color)
                    when (pPieceWhichCheckKindStr) {
                        "Rook" -> {
                            if (pPieceWhichCheckPosition.x == x) {
                                for (i in y..pPieceWhichCheckPosition.y) {
                                    canBlockPositions.add(Position(x, i))
                                }
                            } else if (pPieceWhichCheckPosition.y == y) {
                                for (i in x..pPieceWhichCheckPosition.x) {
                                    canBlockPositions.add(Position(i, y))
                                }
                            }
                        }
                        "Bishop" -> {
                            if (pPieceWhichCheckPosition.x < x) {
                                if (pPieceWhichCheckPosition.y < y) {
                                    for (i in 1..y - pPieceWhichCheckPosition.y) {
                                        canBlockPositions.add(Position(x - i, y - i))
                                    }
                                } else {
                                    for (i in 1..pPieceWhichCheckPosition.y - y) {
                                        canBlockPositions.add(Position(x - i, y + i))
                                    }
                                }
                            } else {
                                if (pPieceWhichCheckPosition.y < y) {
                                    for (i in 1..y - pPieceWhichCheckPosition.y) {
                                        canBlockPositions.add(Position(x + i, y - i))
                                    }
                                } else {
                                    for (i in 1..pPieceWhichCheckPosition.y - y) {
                                        canBlockPositions.add(Position(x + i, y + i))
                                    }
                                }
                            }
                        }
                        "Queen" -> {
                            if (pPieceWhichCheckPosition.x == x) {
                                for (i in y..pPieceWhichCheckPosition.y) {
                                    canBlockPositions.add(Position(x, i))
                                }
                            } else if (pPieceWhichCheckPosition.y == y) {
                                for (i in x..pPieceWhichCheckPosition.x) {
                                    canBlockPositions.add(Position(i, y))
                                }
                            } else {
                                if (pPieceWhichCheckPosition.x < x) {
                                    if (pPieceWhichCheckPosition.y < y) {
                                        for (i in 1..y - pPieceWhichCheckPosition.y) {
                                            canBlockPositions.add(Position(x - i, y - i))
                                        }
                                    } else {
                                        for (i in 1..pPieceWhichCheckPosition.y - y) {
                                            canBlockPositions.add(Position(x - i, y + i))
                                        }
                                    }
                                } else {
                                    if (pPieceWhichCheckPosition.y < y) {
                                        for (i in 1..y - pPieceWhichCheckPosition.y) {
                                            canBlockPositions.add(Position(x + i, y - i))
                                        }
                                    } else {
                                        for (i in 1..pPieceWhichCheckPosition.y - y) {
                                            canBlockPositions.add(Position(x + i, y + i))
                                        }
                                    }
                                }
                            }
                        }
                    }

                    var canBlock = false

                    val canBlockIterator = canBlockPositions.iterator()
                    while (canBlockIterator.hasNext()) {
                        val canBlockPos = canBlockIterator.next()
                        for (i in 0..7) {
                            for (j in 0..7) {
                                when (board[i][j]) {
                                    is Pawn -> {
                                        val pawnCanMovePositions =
                                            (board[i][j] as Pawn).getCanMoveArea(
                                                Position(i, j),
                                                board,
                                                !color
                                            )
                                        val iter = pawnCanMovePositions.iterator()
                                        while (iter.hasNext()) {
                                            if (iter.next() == canBlockPos) {
                                                canBlock = true
                                                android.util.Log.i("ChessGame", "Pawn")
                                            }
                                        }
                                    }
                                    is Rook -> {
                                        val rookCanMovePositions =
                                            (board[i][j] as Rook).getCanMoveArea(
                                                Position(i, j),
                                                board,
                                                !color
                                            )
                                        val iter = rookCanMovePositions.iterator()
                                        while (iter.hasNext()) {
                                            if (iter.next() == canBlockPos) {
                                                canBlock = true
                                                android.util.Log.i("ChessGame", "Rook")
                                            }
                                        }
                                    }
                                    is Knight -> {
                                        val knightCanMovePositions =
                                            (board[i][j] as Knight).getCanMoveArea(
                                                Position(i, j),
                                                board,
                                                !color
                                            )
                                        val iter = knightCanMovePositions.iterator()
                                        while (iter.hasNext()) {
                                            if (iter.next() == canBlockPos) {
                                                canBlock = true
                                                android.util.Log.i("ChessGame", "Knight")
                                            }
                                        }
                                    }
                                    is Bishop -> {
                                        val bishopCanMovePositions =
                                            (board[i][j] as Bishop).getCanMoveArea(
                                                Position(i, j),
                                                board,
                                                !color
                                            )
                                        val iter = bishopCanMovePositions.iterator()
                                        while (iter.hasNext()) {
                                            if (iter.next() == canBlockPos) {
                                                canBlock = true
                                                android.util.Log.i("ChessGame", "Bishop")
                                            }
                                        }
                                    }
                                    is Queen -> {
                                        val queenCanMovePositions =
                                            (board[i][j] as Queen).getCanMoveArea(
                                                Position(i, j),
                                                board,
                                                !color
                                            )
                                        val iter = queenCanMovePositions.iterator()
                                        while (iter.hasNext()) {
                                            if (iter.next() == canBlockPos) {
                                                canBlock = true
                                                android.util.Log.i("ChessGame", "Queen")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (canMoveSet.size == 1 && !canBlock) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun isStaleMate(color: Boolean): Boolean {
        var canMovePositions = HashSet<Position>()
        var isStaleMate = true

        for (i in 0..7) {
            for (j in 0..7) {
                when (board[i][j]) {
                    is Pawn -> canMovePositions =
                        (board[i][j] as Pawn).getCanMoveArea(Position(i, j), board, color)
                    is Rook -> canMovePositions =
                        (board[i][j] as Rook).getCanMoveArea(Position(i, j), board, color)
                    is Knight -> canMovePositions =
                        (board[i][j] as Knight).getCanMoveArea(Position(i, j), board, color)
                    is Bishop -> canMovePositions =
                        (board[i][j] as Bishop).getCanMoveArea(Position(i, j), board, color)
                    is Queen -> canMovePositions =
                        (board[i][j] as Queen).getCanMoveArea(Position(i, j), board, color)
                    is King -> canMovePositions =
                        (board[i][j] as King).getCanMoveArea(Position(i, j), board, color)
                }

                if (canMovePositions.size != 1) {
                    if (canMovePositions.isNotEmpty()) {
                        isStaleMate = false
                    }
                }
            }
        }
        return isStaleMate
    }
}