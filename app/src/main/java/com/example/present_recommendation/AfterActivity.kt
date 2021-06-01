package com.example.present_recommendation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_after_login.*


class AfterActivity : AppCompatActivity() {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var myRef : DatabaseReference = database.getReference()

    data class User(
        var uid : String? = null,
        var email : String? = null,
        var txtName : String? = null,
        var list: MutableList<String> = mutableListOf<String>("1", "2", "3", "4", "5", "6")){

    }

    fun writeNewUser(userId : String, uid : String, email : String, txtName : String, list : MutableList<String>){
        var user = User(uid, email, txtName, list)
        myRef.child("users").child(userId).setValue(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_login)


        button.setOnClickListener(){ // 필터링 저장하기
            var fbAuth = FirebaseAuth.getInstance()
            var fbFire = FirebaseFirestore.getInstance()

            var uid = fbAuth?.uid.toString()
            var uemail = fbAuth?.currentUser?.email.toString()
            var txtName : String = "KaKaoTextFile2020-05-28"
            var list: MutableList<String> = mutableListOf<String>("1", "2", "3", "4", "5", "6")

            writeNewUser(uid, uid, uemail, txtName, list)


            textView.setText(uid.toString()) // 보여주기
            textView2.setText(txtName)
            var a : String = ""
            for(i in 0..list.size-1){
                a = a + " / " + list[i].toString()
            }
            textView3.setText(a)
        }


        button2.setOnClickListener(){
            val intent = Intent(application, AfterAfterActivity::class.java)
            startActivity(intent)
        }





    }





}

