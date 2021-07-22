package com.example.chessgame.shareddata

import com.google.firebase.database.FirebaseDatabase

class DatabaseData {
    companion object {
        private val dataBase = FirebaseDatabase.getInstance()
        private val myRef = dataBase.getReference("data")
        val userProfiles = myRef.child("userProfiles")
        private val game = myRef.child("game1")
        val userData = game.child("userData")
        val boardData = game.child("boardData")
        val firstOrderData = game.child("firstOrderData")
        val orderData = game.child("orderData")
        val checkData = game.child("checkData")
        val chatData = game.child("chatData")
    }
}