package com.example.chessgame.pieces

import android.widget.TextView
import com.example.chessgame.Position
import com.example.chessgame.R

class Rook(color: Boolean) : Piece() {
    private var drawableId: Int = 0
    val colorId = color
    val className = "Rook"

    init {
        drawableId = if (color) {
            R.drawable.wrook
        } else {
            R.drawable.brook
        }
    }

    fun setDrawable(target: TextView) {
        target.setBackgroundResource(drawableId)
    }

    fun getCanMoveArea(
        currentPosition: Position,
        board: Array<Array<Piece>>, isIWhite: Boolean
    ): HashSet<Position> {
        val canMovePositions = HashSet<Position>()

        var x = currentPosition.x
        var y = currentPosition.y

        val primaryX = x
        val primaryY = y

        x = 0
        y = 0

        while (primaryX + x < 8) {
            x++
            if (primaryX + x >= 8 || getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) != -1
            ) {
                break
            }
            canMovePositions.add(Position(primaryX + x, primaryY + y))
        }

        x = 0
        y = 0
        while (primaryY + y < 8) {
            y++
            if (primaryY + y >= 8 || getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) != -1
            ) {
                break
            }
            canMovePositions.add(Position(primaryX + x, primaryY + y))
        }

        x = 0
        y = 0
        while (primaryX + x >= 0) {
            x--
            if (primaryX + x < 0 || getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) != -1
            ) {
                break
            }
            canMovePositions.add(Position(primaryX + x, primaryY + y))
        }

        x = 0
        y = 0
        while (primaryY + y >= 0) {
            y--
            if (primaryY + y < 0 || getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) != -1
            ) {
                break
            }
            canMovePositions.add(Position(primaryX + x, primaryY + y))
        }

        canMovePositions.add(Position(primaryX, primaryY))

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

        x = 0; y = 0

        while (primaryX + x < 8) {
            x++
            if (primaryX + x >= 8) {
                break
            }
            if (colorId) {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            } else {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    break
                }
            }
        }

        x = 0;y = 0

        while (primaryX + x >= 0) {
            x--
            if (primaryX + x < 0) {
                break
            }
            if (colorId) {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            } else {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    break
                }
            }
        }

        x = 0;y = 0

        while (primaryY + y < 8) {
            y++
            if (primaryY + y >= 8) {
                break
            }
            if (colorId) {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            } else {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    break
                }
            }
        }

        x = 0;y = 0

        while (primaryY + y >= 0) {
            y--
            if (primaryY + y < 0) {
                break
            }
            if (colorId) {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            } else {
                if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            }
        }

        if (isIWhite != colorId) {
            targetPos.clear()
        }
        return targetPos
    }
}