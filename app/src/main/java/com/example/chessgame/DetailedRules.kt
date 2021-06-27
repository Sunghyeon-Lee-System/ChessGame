package com.example.chessgame

import com.example.chessgame.pieces.Empty
import com.example.chessgame.pieces.Pawn
import com.example.chessgame.pieces.Piece
import com.example.chessgame.shareddata.MovementOfKingAndRook1Device
import com.example.chessgame.shareddata.MovementOfKingAndRook2Device

class DetailedRules(val board: Array<Array<Piece>>) {
    private fun isCanCastling(): HashSet<String> {

        val state = HashSet<String>()
        if (!MovementOfKingAndRook1Device.isWhiteKingMoved && !MovementOfKingAndRook1Device.isWhiteRightRookMoved
            && board[7][5] is Empty && board[7][6] is Empty
        ) {
            android.util.Log.d("ChessGame", "first if")
            MovementOfKingAndRook1Device.whiteKSC = true
            MovementOfKingAndRook2Device.whiteKSC = true
            state.add("WhiteKSC")
        }
        if (!MovementOfKingAndRook1Device.isWhiteKingMoved && !MovementOfKingAndRook1Device.isWhiteLeftRookMoved
            && board[7][1] is Empty && board[7][2] is Empty && board[7][3] is Empty
        ) {
            android.util.Log.d("ChessGame", "second if")
            MovementOfKingAndRook1Device.whiteQSC = true
            MovementOfKingAndRook2Device.whiteQSC = true
            state.add("WhiteQSC")
        }
        if (!MovementOfKingAndRook1Device.isBlackKingMoved && !MovementOfKingAndRook1Device.isBlackRightRookMoved
            && board[0][5] is Empty && board[0][6] is Empty
        ) {
            android.util.Log.d("ChessGame", "third if")
            MovementOfKingAndRook1Device.blackKSC = true
            MovementOfKingAndRook2Device.blackKSC = true
            state.add("BlackKSC")
        }
        if (!MovementOfKingAndRook1Device.isBlackKingMoved && !MovementOfKingAndRook1Device.isBlackLeftRookMoved
            && board[0][1] is Empty && board[0][2] is Empty && board[0][3] is Empty
        ) {
            android.util.Log.d("ChessGame", "fourth if")
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
}