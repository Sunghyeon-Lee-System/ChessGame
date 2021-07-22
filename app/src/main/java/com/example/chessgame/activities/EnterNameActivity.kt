package com.example.chessgame.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chessgame.ProfileVO
import com.example.chessgame.R
import com.example.chessgame.shareddata.DatabaseData
import java.io.IOException

class EnterNameActivity : AppCompatActivity() {

    private val btnOk = findViewById<Button>(R.id.btuOk)
    private val edtName = findViewById<EditText>(R.id.edtName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)

        btnOk.setOnClickListener {
            if (edtName.text == null) {
                Toast.makeText(this@EnterNameActivity, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                val name = edtName.text.toString()
                DatabaseData.userProfiles.push().setValue(ProfileVO(name))
                try {
                    val fos = openFileOutput("name", MODE_PRIVATE)
                    fos.write(name.toByteArray())
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val intent = Intent(this@EnterNameActivity, TwoDeviceGameActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
                finish()
            }
        }
    }
}