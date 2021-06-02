package com.example.present_recommendation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.notification.NotificationListenerService
import com.example.present_recommendation.GiftActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title ="Present_Recommendation"

        button.setOnClickListener{ // ranking->
            var intent = Intent(applicationContext, GiftActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener{// selectDelivery ->
            var intent = Intent(applicationContext, DeliveryState::class.java)
            startActivity(intent)
        }

    }
}