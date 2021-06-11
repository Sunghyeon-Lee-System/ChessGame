package com.example.chessgame.Pieces

import android.widget.TextView
import com.example.chessgame.Position
import com.example.chessgame.R

class Queen(color: Boolean) : Piece() {
    private var drawableId: Int
    val colorId = color

    init {
        drawableId = if (color) {
            R.drawable.wqueen
        } else {
            R.drawable.bqueen
        }
    }

    fun setDrawable(target: TextView) {
        target.setBackgroundResource(drawableId)
    }

    fun getCanMoveArea(currentPosition: Position, board: Array<Array<Piece>>): LinkedHashSet<Position> {
        val canMovePositions = LinkedHashSet<Position>()

        var crossX = currentPosition.x
        var crossY = currentPosition.y

        val primaryX = crossX
        val primaryY = crossY

        var linearX = 0
        var linearY = 0

        crossX = 0
        crossY = 0

        while (primaryX+linearX < 8) {
            linearX++
            if (primaryX+linearX>=8 || getColorAndIsEmpty(board, Position(primaryX+linearX, primaryY+linearY)) != -1) {
                break
            }
            canMovePositions.add(Position(primaryX+linearX, primaryY+linearY))
        }
        linearX=0; linearY=0
        while (primaryY+linearY < 8) {
            linearY++
            if (primaryY+linearY>=8 || getColorAndIsEmpty(board, Position(primaryX+linearX, primaryY+linearY)) != -1) {
                break
            }
            canMovePositions.add(Position(primaryX+linearX, primaryY+linearY))
        }
        linearX=0; linearY=0
        while (primaryY+linearY >= 0) {
            linearX--
            if (primaryX+linearX<0 || getColorAndIsEmpty(board, Position(primaryX+linearX, primaryY+linearY)) != -1) {
                break
            }
            canMovePositions.add(Position(primaryX+linearX, primaryY+linearY))
        }
        linearX=0; linearY=0
        while (primaryY+linearY >= 0) {
            linearY--
            if (primaryY+linearY<0 || getColorAndIsEmpty(board, Position(primaryX+linearX, primaryY+linearY)) != -1) {
                break
            }
            canMovePositions.add(Position(primaryX+linearX, primaryY+linearY))
        }

        while (primaryX + crossX < 8 && primaryY + crossY < 8) {
            crossX++
            crossY++
            if(primaryX+crossX>=8 || primaryY+crossY>=8 || getColorAndIsEmpty(board, Position(primaryX+crossX, primaryY+crossY))!=-1){
                break
            }
            canMovePositions.add(Position(primaryX + crossX, primaryY + crossY))
        }
        crossX = 0
        crossY = 0
        while (primaryX + crossX >= 0 && primaryY + crossY >= 0) {
            crossX--
            crossY--
            if(primaryX+crossX<0 || primaryY+crossY<0 || getColorAndIsEmpty(board, Position(primaryX+crossX, primaryY+crossY))!=-1){
                break
            }
            canMovePositions.add(Position(primaryX + crossX, primaryY + crossY))
        }
        crossX = 0
        crossY = 0
        while (primaryX + crossX >= 0 && primaryY + crossY < 8) {
            crossX--
            crossY++
            if(primaryX+crossX<0 || primaryY+crossY>=8 || getColorAndIsEmpty(board, Position(primaryX+crossX, primaryY+crossY))!=-1){
                break
            }
            canMovePositions.add(Position(primaryX + crossX, primaryY + crossY))
        }
        crossX = 0
        crossY = 0
        while (primaryX + crossX < 8 && primaryY + crossY >= 0) {
            crossX++
            crossY--
            if(primaryX+crossX>=8 || primaryY+crossY<0 || getColorAndIsEmpty(board, Position(primaryX+crossX, primaryY+crossY))!=-1){
                break
            }
            canMovePositions.add(Position(primaryX + crossX, primaryY + crossY))
        }

        canMovePositions.add(Position(primaryX, primaryY))

        return canMovePositions
    }

    fun isCanEat(currentPosition: Position, board: Array<Array<Piece>>): HashSet<Position> {
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

        if (colorId) {
            while (primaryX + x < 8 && primaryY + y < 8) {
                x++
                y++
                if (primaryX + x >= 8 || primaryY + y >= 8) {
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    targetPos.add(Position(primaryX + x, primaryY + y))
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
                    targetPos.add(Position(primaryX + x, primaryY + y))
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
                    targetPos.add(Position(primaryX + x, primaryY + y))
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
                    targetPos.add(Position(primaryX + x, primaryY + y))
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
                    targetPos.add(Position(primaryX + x, primaryY + y))
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
                    targetPos.add(Position(primaryX + x, primaryY + y))
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
                    targetPos.add(Position(primaryX + x, primaryY + y))
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
                    targetPos.add(Position(primaryX + x, primaryY + y))
                    break
                } else if (getColorAndIsEmpty(board, Position(primaryX + x, primaryY + y)) == 0) {
                    break
                }
            }
        }
        return targetPos
    }
}