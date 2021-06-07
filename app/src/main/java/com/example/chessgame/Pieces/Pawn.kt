package com.example.chessgame.Pieces

import android.widget.TextView
import com.example.chessgame.Position
import com.example.chessgame.R

class Pawn(color: Boolean) : Piece() {
    private var drawableId: Int
    val colorId = color

    init {
        drawableId = if (color) {
            R.drawable.wpawn
        } else {
            R.drawable.bpawn
        }
    }

    fun setDrawable(target: TextView) {
        target.setBackgroundResource(drawableId)
    }

    fun getCanMoveArea(currentPosition: Position, board: Array<Array<Piece>>): LinkedHashSet<Position> {
        val canMovePositions = LinkedHashSet<Position>()

        val x = currentPosition.x
        val y = currentPosition.y

        if (colorId) {
            if (getColorAndIsEmpty(board, Position(x - 1, y)) == -1) {
                canMovePositions.add(Position(x - 1, y))
            } else {
                canMovePositions.add(Position(x, y))
            }

            if (currentPosition.x == 6) {
                if (getColorAndIsEmpty(board, Position(x - 1, y)) == -1) {
                    canMovePositions.add(Position(x - 2, y))
                } else {
                    canMovePositions.add(Position(x - 1, y))
                }
            }
        } else {
            if (getColorAndIsEmpty(board, Position(x + 1, y)) == -1) {
                canMovePositions.add(Position(x + 1, y))
            } else {
                canMovePositions.add(Position(x, y))
            }

            if (currentPosition.x == 1) {
                if (getColorAndIsEmpty(board, Position(x + 1, y)) == -1) {
                    canMovePositions.add(Position(x + 2, y))
                } else {
                    canMovePositions.add(Position(x + 1, y))
                }
            }
        }

        canMovePositions.add(Position(x, y))

        return canMovePositions
    }
}