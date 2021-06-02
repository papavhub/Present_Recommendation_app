package com.example.present_recommendation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class StartActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long=5000 //3s
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_main)
        startLoading()

    }
    private fun startLoading() {
        val handler= Handler()
        handler.postDelayed({finish()}, 2000)
    }
}