package com.example.chessgame.Pieces

import android.widget.TextView
import com.example.chessgame.Position

class Empty : Piece() {
    fun setDrawable(target: TextView) {
        target.setBackgroundResource(0)
    }

    fun getCanMoveArea(cursorPosition: Position, board: Array<Array<Piece>>): HashSet<Position> {
        return HashSet()
    }
}