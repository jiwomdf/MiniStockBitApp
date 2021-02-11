package com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare


import com.google.gson.annotations.SerializedName

data class Weiss(
    @SerializedName("MarketPerformanceRating")
    val marketPerformanceRating: String,
    @SerializedName("Rating")
    val rating: String,
    @SerializedName("TechnologyAdoptionRating")
    val technologyAdoptionRating: String
)