package com.example.chessgame.pieces

import android.widget.TextView
import com.example.chessgame.DetailedRules
import com.example.chessgame.Position
import com.example.chessgame.R

class King(color: Boolean) : Piece() {
    private var drawableId: Int = 0
    val colorId = color
    val className = "King"

    init {
        drawableId = if (color) {
            R.drawable.wking
        } else {
            R.drawable.bking
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
        val canMovePosition = HashSet<Position>()

        val primaryX = currentPosition.x
        val primaryY = currentPosition.y

        val detailedRules = DetailedRules(board)

        var x = primaryX + 1
        var y = primaryY
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                board,
                Position(x, y)
            ) == -1 && !detailedRules.isCheck(x, y, colorId)
        ) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX - 1
        y = primaryY
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                board,
                Position(x, y)
            ) == -1 && !detailedRules.isCheck(x, y, colorId)
        ) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX
        y = primaryY + 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                board,
                Position(x, y)
            ) == -1 && !detailedRules.isCheck(x, y, colorId)
        ) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX
        y = primaryY - 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                board,
                Position(x, y)
            ) == -1 && !detailedRules.isCheck(x, y, colorId)
        ) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX + 1
        y = primaryY + 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                board,
                Position(x, y)
            ) == -1 && !detailedRules.isCheck(x, y, colorId)
        ) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX - 1
        y = primaryY - 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                board,
                Position(x, y)
            ) == -1 && !detailedRules.isCheck(x, y, colorId)
        ) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX - 1
        y = primaryY + 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                board,
                Position(x, y)
            ) == -1 && !detailedRules.isCheck(x, y, colorId)
        ) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX + 1
        y = primaryY - 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                board,
                Position(x, y)
            ) == -1 && !detailedRules.isCheck(x, y, colorId)
        ) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX
        y = primaryY
        canMovePosition.add(Position(x, y))
        if (colorId) {
            if (isInBoard(Position(x + 2, y)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x + 1, y))) {
                    canMovePosition.remove(Position(x + 1, y))
                }
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
            }
            if (isInBoard(Position(x - 2, y)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x - 1, y))) {
                    canMovePosition.remove(Position(x - 1, y))
                }
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
            }
            if (isInBoard(Position(x, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x, y + 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x, y + 1))) {
                    canMovePosition.remove(Position(x, y + 1))
                }
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
            }
            if (isInBoard(Position(x, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x, y - 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x, y - 1))) {
                    canMovePosition.remove(Position(x, y - 1))
                }
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
            }

            if (isInBoard(Position(x + 2, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y + 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
            }
            if (isInBoard(Position(x + 2, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y - 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
            }
            if (isInBoard(Position(x - 2, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y - 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
            }
            if (isInBoard(Position(x - 2, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y + 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
            }

            if (isInBoard(Position(x + 1, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x + 1, y + 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x, y + 1))) {
                    canMovePosition.remove(Position(x, y + 1))
                }
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
            }
            if (isInBoard(Position(x + 1, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x + 1, y - 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x, y - 1))) {
                    canMovePosition.remove(Position(x, y - 1))
                }
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
            }
            if (isInBoard(Position(x - 1, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x - 1, y + 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x, y + 1))) {
                    canMovePosition.remove(Position(x, y + 1))
                }
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
            }
            if (isInBoard(Position(x - 1, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x - 1, y - 2)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x, y - 1))) {
                    canMovePosition.remove(Position(x, y - 1))
                }
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
            }
            if (isInBoard(Position(x + 2, y + 1)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y + 1)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
                if (canMovePosition.contains(Position(x + 1, y))) {
                    canMovePosition.remove(Position(x + 1, y))
                }
            }
            if (isInBoard(Position(x + 2, y - 1)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y - 1)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
                if (canMovePosition.contains(Position(x + 1, y))) {
                    canMovePosition.remove(Position(x + 1, y))
                }
            }
            if (isInBoard(Position(x - 2, y + 1)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y + 1)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
                if (canMovePosition.contains(Position(x - 1, y))) {
                    canMovePosition.remove(Position(x - 1, y))
                }
            }
            if (isInBoard(Position(x - 2, y - 1)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y - 1)
                ) == 0
            ) {
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
                if (canMovePosition.contains(Position(x - 1, y))) {
                    canMovePosition.remove(Position(x - 1, y))
                }
            }
        } else {
            if (isInBoard(Position(x + 2, y)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x + 1, y))) {
                    canMovePosition.remove(Position(x + 1, y))
                }
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
            }
            if (isInBoard(Position(x - 2, y)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x - 1, y))) {
                    canMovePosition.remove(Position(x - 1, y))
                }
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
            }
            if (isInBoard(Position(x, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x, y + 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x, y + 1))) {
                    canMovePosition.remove(Position(x, y + 1))
                }
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
            }
            if (isInBoard(Position(x, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x, y - 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x, y - 1))) {
                    canMovePosition.remove(Position(x, y - 1))
                }
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
            }

            if (isInBoard(Position(x + 2, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y + 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
            }
            if (isInBoard(Position(x + 2, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y - 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
            }
            if (isInBoard(Position(x - 2, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y - 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
            }
            if (isInBoard(Position(x - 2, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y + 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
            }

            if (isInBoard(Position(x + 1, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x + 1, y + 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x, y + 1))) {
                    canMovePosition.remove(Position(x, y + 1))
                }
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
            }
            if (isInBoard(Position(x + 1, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x + 1, y - 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x, y - 1))) {
                    canMovePosition.remove(Position(x, y - 1))
                }
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
            }
            if (isInBoard(Position(x - 1, y + 2)) && getColorAndIsEmpty(
                    board,
                    Position(x - 1, y + 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x, y + 1))) {
                    canMovePosition.remove(Position(x, y + 1))
                }
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
            }
            if (isInBoard(Position(x - 1, y - 2)) && getColorAndIsEmpty(
                    board,
                    Position(x - 1, y - 2)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x, y - 1))) {
                    canMovePosition.remove(Position(x, y - 1))
                }
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
            }
            if (isInBoard(Position(x + 2, y + 1)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y + 1)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x + 1, y + 1))) {
                    canMovePosition.remove(Position(x + 1, y + 1))
                }
                if (canMovePosition.contains(Position(x + 1, y))) {
                    canMovePosition.remove(Position(x + 1, y))
                }
            }
            if (isInBoard(Position(x + 2, y - 1)) && getColorAndIsEmpty(
                    board,
                    Position(x + 2, y - 1)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x + 1, y - 1))) {
                    canMovePosition.remove(Position(x + 1, y - 1))
                }
                if (canMovePosition.contains(Position(x + 1, y))) {
                    canMovePosition.remove(Position(x + 1, y))
                }
            }
            if (isInBoard(Position(x - 2, y + 1)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y + 1)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x - 1, y + 1))) {
                    canMovePosition.remove(Position(x - 1, y + 1))
                }
                if (canMovePosition.contains(Position(x - 1, y))) {
                    canMovePosition.remove(Position(x - 1, y))
                }
            }
            if (isInBoard(Position(x - 2, y - 1)) && getColorAndIsEmpty(
                    board,
                    Position(x - 2, y - 1)
                ) == 1
            ) {
                if (canMovePosition.contains(Position(x - 1, y - 1))) {
                    canMovePosition.remove(Position(x - 1, y - 1))
                }
                if (canMovePosition.contains(Position(x - 1, y))) {
                    canMovePosition.remove(Position(x - 1, y))
                }
            }
        }

        if (isIWhite != colorId) {
            canMovePosition.clear()
        }

        return canMovePosition
    }

    fun isCanEat(
        currentPosition: Position,
        board: Array<Array<Piece>>,
        isIWhite: Boolean
    ): HashSet<Position> {
        val primaryX = currentPosition.x
        val primaryY = currentPosition.y

        val canMovePosition = HashSet<Position>()

        if (colorId) {
            var x = primaryX + 1
            var y = primaryY
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 0
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX - 1
            y = primaryY
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 0
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX
            y = primaryY + 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 0
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX
            y = primaryY - 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 0
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX + 1
            y = primaryY + 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 0
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX - 1
            y = primaryY - 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 0
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX - 1
            y = primaryY + 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 0
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX + 1
            y = primaryY - 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 0
            ) {
                canMovePosition.add(Position(x, y))
            }
        } else {
            var x = primaryX + 1
            var y = primaryY
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 1
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX - 1
            y = primaryY
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 1
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX
            y = primaryY + 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 1
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX
            y = primaryY - 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 1
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX + 1
            y = primaryY + 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 1
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX - 1
            y = primaryY - 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 1
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX - 1
            y = primaryY + 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 1
            ) {
                canMovePosition.add(Position(x, y))
            }

            x = primaryX + 1
            y = primaryY - 1
            if (isInBoard(Position(x, y)) && getColorAndIsEmpty(
                    board,
                    Position(x, y)
                ) == 1
            ) {
                canMovePosition.add(Position(x, y))
            }
        }

        if (isIWhite != colorId) {
            canMovePosition.clear()
        }
        return canMovePosition
    }
}