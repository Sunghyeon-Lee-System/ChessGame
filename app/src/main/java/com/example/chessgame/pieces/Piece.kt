package com.example.chessgame.pieces

import com.example.chessgame.Position

open class Piece {
    val color = true
    var onCanMove=false

    fun isInBoard(position: Position): Boolean {
        val x = position.x
        val y = position.y

        if (x in 0..7 && y in 0..7) {
            return true
        }
        return false
    }

    fun getColorAndIsEmpty(array: Array<Array<Piece>>, pos: Position): Int {
        val x = pos.x
        val y = pos.y

        val targetPiece = array[x][y]
        var pieceColor = true
        when (targetPiece) {
            is Empty -> return -1
            is Pawn -> pieceColor = targetPiece.colorId
            is Rook -> pieceColor = targetPiece.colorId
            is Knight -> pieceColor = targetPiece.colorId
            is Bishop -> pieceColor = targetPiece.colorId
            is Queen -> pieceColor = targetPiece.colorId
            is King -> pieceColor = targetPiece.colorId
        }
        return if (pieceColor) {
            1
        } else {
            0
        }
    }
}