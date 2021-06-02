package com.example.present_recommendation

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        var exit = findViewById<ImageButton>(R.id.exitBtn)

        exit.setOnClickListener {
            finish()
        }
    }
}