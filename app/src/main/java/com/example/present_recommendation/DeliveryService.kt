package com.example.present_recommendation

import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface DeliveryService {

    @GET("carriers/{DeliveryCompany}/tracks/{TrackingNumber}/")
    fun requestDelivery(
            @Path("DeliveryCompany") DeliveryCompany: String,
            @Path("TrackingNumber") TrackingNumber : String
    ) : Call<Delivery>

    @GET("carriers")
    fun requestInquiry(): Call<ArrayList<Inquiry>>
}