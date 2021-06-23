package com.example.chessgame.pieces

import android.widget.TextView
import com.example.chessgame.Position
import com.example.chessgame.R

class Pawn(color: Boolean) : Piece() {
    private var drawableId: Int
    val colorId = color
    val className = "Pawn"

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

    fun getCanMoveArea(
        currentPosition: Position,
        board: Array<Array<Piece>>,
        isIWhite: Boolean
    ): HashSet<Position> {
        val canMovePositions = HashSet<Position>()

        val x = currentPosition.x
        val y = currentPosition.y

        if (colorId) {
            if (getColorAndIsEmpty(board, Position(x - 1, y)) == -1) {
                canMovePositions.add(Position(x - 1, y))
            }

            if (currentPosition.x == 6) {
                if (getColorAndIsEmpty(board, Position(x - 1, y)) == -1 && getColorAndIsEmpty(
                        board,
                        Position(x - 2, y)
                    ) == -1
                ) {
                    canMovePositions.add(Position(x - 2, y))
                }
            }
        } else {
            if (getColorAndIsEmpty(board, Position(x + 1, y)) == -1) {
                canMovePositions.add(Position(x + 1, y))
            }

            if (currentPosition.x == 1) {
                if (getColorAndIsEmpty(board, Position(x + 1, y)) == -1 && getColorAndIsEmpty(
                        board,
                        Position(x + 2, y)
                    ) == -1
                ) {
                    canMovePositions.add(Position(x + 2, y))
                }
            }
        }

        canMovePositions.add(Position(x, y))

        if (isIWhite != colorId) {
            canMovePositions.clear()
        }

        return canMovePositions
    }

    fun isCanEat(
        currentPosition: Position,
        board: Array<Array<Piece>>,
        isIWhite: Boolean
    ): HashSet<Position> {
        var x = currentPosition.x
        var y = currentPosition.y

        val primaryX = x
        val primaryY = y

        val targetPos = HashSet<Position>()

        if (colorId) {
            x = -1;y = 1
            if (primaryY != 7) {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                }
            }
            x = -1;y = -1
            if (primaryY != 0) {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                }
            }
        } else {
            x = 1;y = 1
            if (primaryY != 7) {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                }
            }
            x = 1;y = -1
            if (primaryY != 0) {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                }
            }
        }

        if (isIWhite != colorId) {
            targetPos.clear()
        }
        return targetPos
    }
}