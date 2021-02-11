package com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare


import com.google.gson.annotations.SerializedName

data class DISPLAY(
    @SerializedName("USD")
    val uSD: USD
)