package com.example.present_recommendation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.ranking.*


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


                val txtN : String = snapshot?.child("users").child(uid).value.toString()
                val array = txtN.split("]}, " ).toMutableList()

                Log.d("test",txtN)

                array[0] = array[0].substring(1, array[0].length)

                for(index in array.indices){
                    array[index] = array[index].replace("={list1", "*")
                }

                for(index in array.indices){
                    array[index] = array[index].substring(0, array[index].indexOf('*'))
                }

                var List : MutableList<String> = arrayListOf()

                for(index in array.indices){
                    List.add(array[index])
                }

                List.toTypedArray()

                var my = List as Array<String>

                var adapter3 : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, my)

                listView.adapter = adapter3



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }


}

