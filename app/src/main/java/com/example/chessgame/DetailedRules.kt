package com.example.chessgame

import com.example.chessgame.pieces.Empty
import com.example.chessgame.pieces.Piece

class DetailedRules(val board: Array<Array<Piece>>) {
    private fun isCanCastling(): HashSet<String> {

        val state = HashSet<String>()
        if (!MovementOfKindAndRook.isWhiteKingMoved && !MovementOfKindAndRook.isWhiteRightRookMoved
            && board[7][5] is Empty && board[7][6] is Empty
        ) {
            android.util.Log.d("ChessGame", "first if")
            MovementOfKindAndRook.whiteKSC = true
            state.add("WhiteKSC")
        }
        if (!MovementOfKindAndRook.isWhiteKingMoved && !MovementOfKindAndRook.isWhiteLeftRookMoved
            && board[7][1] is Empty && board[7][2] is Empty && board[7][3] is Empty
        ) {
            android.util.Log.d("ChessGame", "second if")
            MovementOfKindAndRook.whiteQSC = true
            state.add("WhiteQSC")
        }
        if (!MovementOfKindAndRook.isBlackKingMoved && !MovementOfKindAndRook.isBlackRightRookMoved
            && board[0][5] is Empty && board[0][6] is Empty
        ) {
            android.util.Log.d("ChessGame", "third if")
            MovementOfKindAndRook.blackKSC = true
            state.add("BlackKSC")
        }
        if (!MovementOfKindAndRook.isBlackKingMoved && !MovementOfKindAndRook.isBlackLeftRookMoved
            && board[0][1] is Empty && board[0][2] is Empty && board[0][3] is Empty
        ) {
            android.util.Log.d("ChessGame", "fourth if")
            MovementOfKindAndRook.blackQSC = true
            state.add("BlackQSC")
        }

        return state
    }

    fun castlingManager(): HashSet<Position>? {
        val positionSet=HashSet<Position>()

        val state=isCanCastling()
        val iterator=state.iterator()

        while(iterator.hasNext()){
            when(iterator.next()){
                "WhiteKSC" -> positionSet.add(Position(7, 6))
                "WhiteQSC" -> positionSet.add(Position(7, 2))
                "BlackKSC" -> positionSet.add(Position(0, 6))
                "BlackQSC" -> positionSet.add(Position(0, 2))
            }
        }
        return if(positionSet.isEmpty()){
            null
        }else{
            positionSet
        }
    }
}