package com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare


import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("Count")
    val count: Int
)