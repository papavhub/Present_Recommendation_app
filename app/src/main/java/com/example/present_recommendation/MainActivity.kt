package com.example.present_recommendation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.notification.NotificationListenerService
import com.example.present_recommendation.GiftActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent= Intent(this, StartActivity::class.java)
        startActivity(intent)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{ // 선물 추천
            var intent = Intent(applicationContext, GiftActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener{// 배송 조회
            var intent = Intent(applicationContext, DeliveryState::class.java)
            startActivity(intent)
        }

        button5.setOnClickListener{// 로그인 버튼
            var intent = Intent(applicationContext, LoginMainActivity::class.java)
            startActivity(intent)
       }
    }
}