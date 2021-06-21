package com.example.chessgame.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.chessgame.R

class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        val handler= Handler()

        handler.postDelayed(Runnable {
           val intent= Intent(this, ChoicePlayerActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}