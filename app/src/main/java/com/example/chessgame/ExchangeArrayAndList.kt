package com.example.chessgame

import com.example.chessgame.pieces.*

class ExchangeArrayAndList {
    companion object {
        fun arrayToList(array: Array<Array<Piece>>): ArrayList<Piece> {
            val list = ArrayList<Piece>()

            for (i in array.indices) {
                for (j in array[0].indices) {
                    list.add(array[i][j])
                }
            }
            return list
        }

        fun listToPiece(dbMap: ArrayList<HashMap<String, Any>>) : Array<Array<Piece>> {
            var piece=Piece()
            val pieceArray = Array(8){
                Array<Piece>(8){
                    Empty()
                }
            }

            for (i in 0..7) {
                for (j in 0..7) {
                    val tileMap = dbMap[i*8+j]
                    val colorId = tileMap["colorId"]
                    val onCanMove = tileMap["onCanMove"]
                    when (tileMap["className"]) {
                        "Pawn" -> piece = Pawn(colorId as Boolean)
                        "Rook" -> piece = Rook(colorId as Boolean)
                        "Knight" -> piece = Knight(colorId as Boolean)
                        "Bishop" -> piece = Bishop(colorId as Boolean)
                        "Queen" -> piece = Queen(colorId as Boolean)
                        "King" -> piece = King(colorId as Boolean)
                        "Empty" -> piece = Empty()
                    }
                    piece.onCanMove = onCanMove as Boolean

                    pieceArray[i][j]=piece
                }
            }
            return pieceArray
        }
    }
}