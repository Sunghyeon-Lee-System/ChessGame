package com.example.chessgame

data class Position(var x: Int, var y: Int) {
    override fun equals(other: Any?): Boolean {
        val position = other as Position
        if (position.x == x && position.y == y) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        return x + y + 137
    }
}