package com.example.chessgame.Pieces

import android.widget.TextView
import com.example.chessgame.Position
import com.example.chessgame.R

class Knight(color: Boolean) : Piece() {
    private var drawableId: Int
    val colorId = color

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

    fun getCanMoveArea(currentPosition: Position, board: Array<Array<Piece>>): LinkedHashSet<Position> {
        val canMovePosition = LinkedHashSet<Position>()

        val primaryX = currentPosition.x
        val primaryY = currentPosition.y

        var x = primaryX - 1
        var y = primaryY - 2
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(board, Position(x, y)) == -1) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX + 1
        y = primaryX - 2
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(board, Position(x, y)) == -1) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX - 1
        y = primaryY + 2
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(board, Position(x, y)) == -1) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX + 1
        y = primaryY + 2
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(board, Position(x, y)) == -1) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX - 2
        y = primaryY - 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(board, Position(x, y)) == -1) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX - 2
        y = primaryY + 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(board, Position(x, y)) == -1) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX + 2
        y = primaryY - 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(board, Position(x, y)) == -1) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX + 2
        y = primaryY + 1
        if (isInBoard(Position(x, y)) && getColorAndIsEmpty(board, Position(x, y)) == -1) {
            canMovePosition.add(Position(x, y))
        }

        x = primaryX
        y = primaryY
        canMovePosition.add(Position(x, y))

        return canMovePosition
    }
}