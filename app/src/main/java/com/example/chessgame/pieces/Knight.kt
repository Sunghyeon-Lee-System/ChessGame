package com.example.chessgame.pieces

import android.widget.TextView
import com.example.chessgame.Position
import com.example.chessgame.R

class Knight(color: Boolean) : Piece() {
    private var drawableId: Int = 0
    val colorId = color
    val className = "Knight"

    init {
        drawableId = if (color) {
            R.drawable.wknight
        } else {
            R.drawable.bknight
        }
    }

    fun setDrawable(target: TextView) {
        target.setBackgroundResource(drawableId)
    }

    fun getCanMoveArea(currentPosition: Position, board: Array<Array<Piece>>): HashSet<Position> {
        val canMovePosition = HashSet<Position>()

        val primaryX = currentPosition.x
        val primaryY = currentPosition.y

        var x = 2
        var y = 1
        if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                board,
                Position(primaryX + x, primaryY + y)
            ) == -1
        ) {
            canMovePosition.add(Position(primaryX + x, primaryY + y))
        }

        x = 2
        y = -1
        if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                board,
                Position(primaryX + x, primaryY + y)
            ) == -1
        ) {
            canMovePosition.add(Position(primaryX + x, primaryY + y))
        }

        x = -2
        y = 1
        if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                board,
                Position(primaryX + x, primaryY + y)
            ) == -1
        ) {
            canMovePosition.add(Position(primaryX + x, primaryY + y))
        }

        x = -2
        y = -1
        if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                board,
                Position(primaryX + x, primaryY + y)
            ) == -1
        ) {
            canMovePosition.add(Position(primaryX + x, primaryY + y))
        }

        x = 1
        y = 2
        if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                board,
                Position(primaryX + x, primaryY + y)
            ) == -1
        ) {
            canMovePosition.add(Position(primaryX + x, primaryY + y))
        }

        x = 1
        y = -2
        if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                board,
                Position(primaryX + x, primaryY + y)
            ) == -1
        ) {
            canMovePosition.add(Position(primaryX + x, primaryY + y))
        }

        x = -1
        y = 2
        if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                board,
                Position(primaryX + x, primaryY + y)
            ) == -1
        ) {
            canMovePosition.add(Position(primaryX + x, primaryY + y))
        }

        x = -1
        y = -2
        if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                board,
                Position(primaryX + x, primaryY + y)
            ) == -1
        ) {
            canMovePosition.add(Position(primaryX + x, primaryY + y))
        }

        canMovePosition.add(Position(primaryX, primaryY))

        return canMovePosition
    }

    fun isCanEat(currentPosition: Position, board: Array<Array<Piece>>): HashSet<Position> {
        val targetPos = HashSet<Position>()

        val primaryX = currentPosition.x
        val primaryY = currentPosition.y

        if (colorId) {
            var x = 2
            var y = 1
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 0
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = 2
            y = -1
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 0
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = -2
            y = 1
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 0
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = -2
            y = -1
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 0
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = 1
            y = 2
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 0
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = 1
            y = -2
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 0
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = -1
            y = 2
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 0
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = -1
            y = -2
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 0
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }
        } else {
            var x = 2
            var y = 1
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 1
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = 2
            y = -1
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 1
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = -2
            y = 1
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 1
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = -2
            y = -1
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 1
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = 1
            y = 2
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 1
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = 1
            y = -2
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 1
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = -1
            y = 2
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 1
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }

            x = -1
            y = -2
            if (isInBoard(Position(primaryX + x, primaryY + y)) && getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) == 1
            ) {
                targetPos.add(Position(primaryX + x, primaryY + y))
            }
        }
        return targetPos
    }
}