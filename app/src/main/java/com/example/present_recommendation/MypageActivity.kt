package com.example.present_recommendation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mypage.*


class MypageActivity : AppCompatActivity() {


    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var myRef: DatabaseReference = database.getReference()

    var fbAuth = FirebaseAuth.getInstance()
    var fbFire = FirebaseFirestore.getInstance()
    var uid = fbAuth?.uid.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)


        /*val txtN = myRef.child("users").child(uid).key
        textView3.text = "$txtN."*/

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val txtN = snapshot?.child("users").child(uid).value
                val List : String = ""

                textView3.text = "$txtN"


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }

}

