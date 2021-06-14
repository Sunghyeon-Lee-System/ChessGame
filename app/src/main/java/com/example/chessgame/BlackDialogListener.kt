package com.example.chessgame

import com.example.chessgame.pieces.Piece

interface BlackDialogListener {
    fun onClicked(type: Piece)
}