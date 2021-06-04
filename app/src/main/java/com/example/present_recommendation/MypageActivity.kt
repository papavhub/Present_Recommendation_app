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
import kotlinx.android.synthetic.main.activity_afterafter.*
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

        var my : Array<String> = arrayOf()


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

                my = List.toTypedArray()


                /*var adapter : ArrayAdapter<String>
                adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, my)

                LV.adapter = adapter*/

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        back4.setOnClickListener{
            finish()
        }

        button8.setOnClickListener{

            var adapter : ArrayAdapter<String>
            //adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, my)
            adapter = ArrayAdapter(this, R.layout.fontstyleblack, my)

            LV.adapter = adapter

            LV.setOnItemClickListener{parent, view, position, id->

                myRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        var list1 = snapshot?.child("users").child(uid).child(my[position]).child("list1").value
                        var list2 = snapshot?.child("users").child(uid).child(my[position]).child("list2").value


                        //textView3.setText(list1.toString() + "\n" + list2.toString())

                        var array1 : MutableList<String> = list1 as MutableList<String>
                        var array2 : MutableList<String> = list2 as MutableList<String>



                        var myarray : Array<String> = arrayOf()
                        myarray = array1.toTypedArray()

                        var topten : Array<String> = arrayOf()
                        topten = array2.toTypedArray()


                        var intent = Intent(applicationContext, RankingActivity::class.java)

                        intent.putExtra("recommend_list_top_10", myarray)
                        intent.putExtra("strong_recommend_list_top_10", topten)

                        intent.putExtra("txtFileName", my[position]) // txt 이름 가져가기기

                        startActivity(intent)

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }

        }



    }


}

