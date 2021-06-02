package com.example.present_recommendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.deliverystate.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeliveryState  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deliverystate)

        var t1 = findViewById<TextView>(R.id.t1)
        var t2 = findViewById<TextView>(R.id.t2)
        var t3 = findViewById<TextView>(R.id.t3)

        val items = arrayOf(
                "CJ대한통운","한진택배","우체국택배"
        )

        val myAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,items)

        spinner.adapter = myAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    else -> {

                    }

                }
            }
        }


        val retrofit = Retrofit.Builder()
                .baseUrl("https://apis.tracker.delivery/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val deliverService: DeliveryService = retrofit.create(DeliveryService::class.java)

        deliveryBtn.setOnClickListener {

            //대한통운
            when (spinner.selectedItemPosition) {
                0 -> {
                    val trackingNum = trackingNumber.text.toString()
                    val deliveryInfo = deliverService.requestDelivery("kr.cjlogistics",trackingNum)
                    deliveryInfo.enqueue(object : Callback<Delivery> {
                        override fun onResponse(
                                call: Call<Delivery>,
                                response: Response<Delivery>
                        ) {
                            for(i in 0 until response.body()?.progresses?.size!!)
                            {

                                val progresses = response.body()?.progresses!![i]
                                val timeStr = progresses.time
                                val realTime = timeStr.substring(0,4) +
                                        "년"+ timeStr.substring(5,7) +
                                        "월"+ timeStr.substring(8,10) +
                                        "일"+ timeStr.substring(11,13) +
                                        "시"+ timeStr.substring(14,16) +
                                        "분"

                                Log.d("TAG", "시간 : $realTime")
                                Log.d("TAG","현재위치 : ${progresses.location.name}")
                                Log.d("TAG","배송상태 : ${progresses.status.text}")


                                var t : String = t1.getText().toString()////////////////////////////////
                                t = t + " $realTime // "
                                t1.setText(t)

                                t = t2.getText().toString()
                                t = t + "${progresses.location.name} //"
                                t2.setText(t)

                                t = t3.getText().toString()
                                t = t + "${progresses.status.text} //"
                                t3.setText(t)

                            }
                        }

                        override fun onFailure(call: Call<Delivery>, t: Throwable) {

                            Log.d("TAG", "실패 : ${t.message}")

                        }
                    })
                }

                //한진택배
                1 -> {
                    val trackingNum = trackingNumber.text.toString()
                    val deliveryInfo = deliverService.requestDelivery("kr.hanjin",trackingNum)
                    deliveryInfo.enqueue(object : Callback<Delivery> {
                        override fun onResponse(
                                call: Call<Delivery>,
                                response: Response<Delivery>
                        ) {
                            for(i in 0 until response.body()?.progresses?.size!!)
                            {

                                val progresses = response.body()?.progresses!![i]
                                val timeStr = progresses.time
                                val realTime = timeStr.substring(0,4) +
                                        "년"+ timeStr.substring(5,7) +
                                        "월"+ timeStr.substring(8,10) +
                                        "일"+ timeStr.substring(11,13) +
                                        "시"+ timeStr.substring(14,16) +
                                        "분"

                                Log.d("TAG", "시간 : $realTime")
                                Log.d("TAG","현재위치 : ${progresses.location.name}")
                                Log.d("TAG","배송상태 : ${progresses.status.text}")

                                var t : String = t1.getText().toString()////////////////////////////////
                                t = t + " $realTime // "
                                t1.setText(t)

                                t = t2.getText().toString()
                                t = t + "${progresses.location.name} //"
                                t2.setText(t)

                                t = t3.getText().toString()
                                t = t + "${progresses.status.text} //"
                                t3.setText(t)

                            }
                        }

                        override fun onFailure(call: Call<Delivery>, t: Throwable) {
                            Log.d("TAG", "실패 : ${t.message}")
                        }
                    })
                }

                //우체국택배
                2 -> {
                    val trackingNum = trackingNumber.text.toString()
                    val deliveryInfo = deliverService.requestDelivery("kr.epost",trackingNum)
                    deliveryInfo.enqueue(object : Callback<Delivery> {
                        override fun onResponse(
                                call: Call<Delivery>,
                                response: Response<Delivery>
                        ) {
                            for(i in 0 until response.body()?.progresses?.size!!)
                            {
                                val progresses = response.body()?.progresses!![i]
                                val timeStr = progresses.time
                                val realTime = timeStr.substring(0,4) +
                                        "년"+ timeStr.substring(5,7) +
                                        "월"+ timeStr.substring(8,10) +
                                        "일"+ timeStr.substring(11,13) +
                                        "시"+ timeStr.substring(14,16) +
                                        "분"

                                Log.d("TAG", "시간 : $realTime")
                                Log.d("TAG","현재위치 : ${progresses.location.name}")
                                Log.d("TAG","배송상태 : ${progresses.status.text}")

                                var t : String = t1.getText().toString()////////////////////////////////
                                t = t + " $realTime // "
                                t1.setText(t)

                                t = t2.getText().toString()
                                t = t + "${progresses.location.name} //"
                                t2.setText(t)

                                t = t3.getText().toString()
                                t = t + "${progresses.status.text} //"
                                t3.setText(t)
                            }
                        }

                        override fun onFailure(call: Call<Delivery>, t: Throwable) {
                            Log.d("TAG", "실패 : ${t.message}")
                        }
                    })
                }
            }
        }
    }
}