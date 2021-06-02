package com.example.present_recommendation

import android.graphics.Insets.add
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets.add
import androidx.core.view.OneShotPreDrawListener.add
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_afterafter.*

// 불러오기 코드

class AfterAfterActivity : AppCompatActivity() {

    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var myRef: DatabaseReference = database.getReference()

    var fbAuth = FirebaseAuth.getInstance()
    var fbFire = FirebaseFirestore.getInstance()
    var uid = fbAuth?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afterafter)

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val UID = snapshot?.child("users").child(uid).child("uid").value
                textView4.text = "uid : $UID"

                val txtName = snapshot?.child("users").child(uid).child("txtName").value
                textView5.text = "txtName : $txtName"

                val email = snapshot?.child("users").child(uid).child("email").value
                textView6.text = "email : $email"

                val list = snapshot?.child("users").child(uid).child("list").value
                textView7.text = "list : $list"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
