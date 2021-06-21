package com.example.chessgame.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chessgame.R
import kotlinx.android.synthetic.main.activity_choice_player.*

class ChoicePlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_player)

        btn_1.setOnClickListener {

        }

        btn_2.setOnClickListener{
            val intent= Intent(this, EnterNameActivity::class.java)
            startActivity(intent)
        }
    }
}