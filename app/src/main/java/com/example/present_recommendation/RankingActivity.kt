package com.example.present_recommendation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ranking.*
import com.example.present_recommendation.R
import com.example.present_recommendation.GiftActivity



class RankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ranking)

        var intent = intent
        var myarray : Array<String> = intent.getStringArrayExtra("recommend_list_top_10") as Array<String>
        var topten : Array<String> = intent.getStringArrayExtra("strong_recommend_list_top_10") as Array<String>

        var adapter1 : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, myarray)
        var adapter2 : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, topten)

        listView1.adapter = adapter1
        listView2.adapter = adapter2

        listView1.setOnItemClickListener { parent, view, position, id ->
            var word : String = myarray[position].toString()

            var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
            var uri = Uri.parse(url)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


        listView2.setOnItemClickListener{parent, view, position, id ->
            var word : String = topten[position].toString()

            var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
            var uri = Uri.parse(url)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


        button3.setOnClickListener{ // 홈으로 버튼
            finish()
        }

        button4.setOnClickListener{ // 다른 대화내용 불러오기 버튼
            finish()
        }


    }
}









/*for(i in 0..myarray.count()-1) { // 버튼 생성
            var btn = Button(this).apply{
                setOnClickListener{
                    var word : String = myarray[i]

                    var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
                    var uri = Uri.parse(url)
                    var intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
            btn.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            btn.id = i.toInt()
            btn.setText(myarray[i])
            linear1.addView(btn)
        }
        for(i in 0..topten.count()-1) { // 버튼 생성
            var btn = Button(this).apply{
                setOnClickListener{
                    var word : String = topten[i]
                    var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
                    var uri = Uri.parse(url)
                    var intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
            btn.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            btn.id = (i+10).toInt()
            btn.setText(topten[i])
            linear2.addView(btn)
        }*/