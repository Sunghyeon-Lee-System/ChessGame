package com.example.chessgame.pieces

import android.widget.TextView
import com.example.chessgame.Position
import com.example.chessgame.R

class Bishop(color: Boolean) : Piece() {
    private var drawableId: Int
    val colorId = color

    init {
        drawableId = if (color) {
            R.drawable.wbishop
        } else {
            R.drawable.bbishop
        }
    }

    fun setDrawable(target: TextView) {
        target.setBackgroundResource(drawableId)
    }

    fun getCanMoveArea(currentPosition: Position, board: Array<Array<Piece>>): HashSet<Position> {
        val canMovePositions = HashSet<Position>()

        var x = currentPosition.x
        var y = currentPosition.y

        val primaryX = x
        val primaryY = y

        x = 0
        y = 0

        canMovePositions.add(Position(primaryX, primaryY))

        while (primaryX + x < 8 && primaryY + y < 8) {
            x++
            y++
            if ((primaryX + x >= 8 || primaryY + y >= 8) || getColorAndIsEmpty(
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
        while (primaryX + x >= 0 && primaryY + y >= 0) {
            x--
            y--
            if (primaryX + x < 0 || primaryY + y < 0 || getColorAndIsEmpty(
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
        while (primaryX + x >= 0 && primaryY + y < 8) {
            x--
            y++
            if (primaryX + x < 0 || primaryY + y >= 8 || getColorAndIsEmpty(
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
        while (primaryX + x >= 8 || primaryY + y < 0 || primaryX + x < 8 && primaryY + y >= 0) {
            x++
            y--
            if (primaryX + x >= 8 || primaryY + y < 0 || getColorAndIsEmpty(
                    board,
                    Position(primaryX + x, primaryY + y)
                ) != -1
            ) {
                break
            }
            canMovePositions.add(Position(primaryX + x, primaryY + y))
        }
        return canMovePositions
    }

    fun isCanEat(currentPosition: Position, board: Array<Array<Piece>>): HashSet<Position> {
        val canMovePositions = HashSet<Position>()

        val primaryX = currentPosition.x
        val primaryY = currentPosition.y

        var x = 0
        var y = 0

        if (colorId) {
            while (primaryX + x < 8 && primaryY + y < 8) {
                x++
                y++
                if (primaryX + x >= 8 || primaryY + y >= 8) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    canMovePositions.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            }
            x = 0;y = 0
            while (primaryX + x >= 0 && primaryY + y < 8) {
                x--
                y++
                if (primaryX + x < 0 || primaryY + y >= 8) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    canMovePositions.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            }
            x = 0;y = 0
            while (primaryX + x < 8 && primaryY + y >= 0) {
                x++
                y--
                if (primaryX + x >= 8 || primaryY + y < 0) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    canMovePositions.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            }
            x = 0;y = 0
            while (primaryX + x >= 0 && primaryY + y >= 0) {
                x--
                y--
                if (primaryX + x < 0 || primaryY + y < 0) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    canMovePositions.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    break
                }
            }
        } else {
            while (primaryX + x < 8 && primaryY + y < 8) {
                x++
                y++
                if (primaryX + x >= 8 || primaryY + y >= 8) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    canMovePositions.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    break
                }
            }
            x = 0;y = 0
            while (primaryX + x >= 0 && primaryY + y < 8) {
                x--
                y++
                if (primaryX + x < 0 || primaryY + y >= 8) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    canMovePositions.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    break
                }
            }
            x = 0;y = 0
            while (primaryX + x < 8 && primaryY + y >= 0) {
                x++
                y--
                if (primaryX + x >= 8 || primaryY + y < 0) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    canMovePositions.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    break
                }
            }
            x = 0;y = 0
            while (primaryX + x >= 0 && primaryY + y >= 0) {
                x--
                y--
                if (primaryX + x < 0 || primaryY + y < 0) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 1) {
                    canMovePositions.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    break
                }
            }
        }
        return canMovePositions
    }
}