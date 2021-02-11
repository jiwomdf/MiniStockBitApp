package com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare


import com.google.gson.annotations.SerializedName

data class RAW(
    @SerializedName("USD")
    val uSD: USDX
)