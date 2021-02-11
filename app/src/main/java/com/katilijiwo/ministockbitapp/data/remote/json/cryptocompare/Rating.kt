package com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("Weiss")
    val weiss: Weiss
)