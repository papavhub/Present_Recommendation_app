package com.example.present_recommendation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        button.setOnClickListener{ // 선물 추천
            var intent = Intent(applicationContext, GiftActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener{// 배송 조회
            var intent = Intent(applicationContext, DeliveryState::class.java)
            startActivity(intent)
        }

        button5.setOnClickListener{// 로그인 버튼
            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
       }
    }
}