package com.example.chessgame

import com.example.chessgame.pieces.Piece

interface WhiteDialogListener {
    fun onClicked(type: Piece)
}