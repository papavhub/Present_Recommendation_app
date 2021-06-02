package com.example.present_recommendation

import com.google.gson.annotations.SerializedName


data class Delivery(

        val from: FromData,
        val to: ToData,
        val state: StateData,
        val progresses: List<ProgressesData>,
        val carrier: CarrierData

)

data class FromData(
        @SerializedName("name")
        var name:String = "",

        @SerializedName("time")
        var time:String = ""
)

data class ToData(
        @SerializedName("name")
        var name:String = "",

        @SerializedName("time")
        var time:String = ""
)

data class StateData(
        @SerializedName("id")
        var id:String = "",

        @SerializedName("text")
        var text:String = ""
)

data class CarrierData(
        @SerializedName("id")
        var id:String = "",

        @SerializedName("name")
        var name:String = "",

        @SerializedName("tel")
        var tel:String = ""

)

data class ProgressesData(

        @SerializedName("time")
        var time:String = "",

        @SerializedName("status")
        var status: StatusData,

        @SerializedName("location")
        var location: LocationData,

        @SerializedName("description")
        var description: String = ""
)

data class StatusData(
        @SerializedName("id")
        var id:String ="",

        @SerializedName("text")
        var text:String = ""
)

data class LocationData(
        @SerializedName("name")
        var name: String = ""
)
