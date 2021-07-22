package com.example.chessgame.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.chessgame.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ChoicePlayerActivity : AppCompatActivity() {
    private val btn_1 = findViewById<Button>(R.id.btn_1)
    private val btn_2 = findViewById<Button>(R.id.btn_2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_player)

        btn_1.setOnClickListener {
            val intent = Intent(this, OneDeviceGameActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_2.setOnClickListener {
            try {
                val fis = openFileInput("name")
                val isr = InputStreamReader(fis)
                val br = BufferedReader(isr)
                val name = br.readLine()
                if (fis == null) {
                    val intent = Intent(this@ChoicePlayerActivity, EnterNameActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent =
                        Intent(this@ChoicePlayerActivity, TwoDeviceGameActivity::class.java)
                    intent.putExtra("name", name)
                    startActivity(intent)
                    finish()
                }
                br.close()
                isr.close()
                fis.close()
            } catch (e: IOException) {
                val intent = Intent(this@ChoicePlayerActivity, EnterNameActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}