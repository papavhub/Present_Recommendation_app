package com.example.present_recommendation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        /*var myPage = findViewById<Button>(R.id.mypage)
        var login = findViewById<Button>(R.id.login)*/


        //        마이페이지
        mypage.setOnClickListener {
            var intent = Intent(this, MypageActivity::class.java)
            startActivity(intent);
        }

// 로그아웃
        login.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }





        button.setOnClickListener{ // 선물 추천
            var intent = Intent(applicationContext, GiftActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener{// 배송 조회
            var intent = Intent(applicationContext, DeliveryState::class.java)
            startActivity(intent)
        }

    }
}