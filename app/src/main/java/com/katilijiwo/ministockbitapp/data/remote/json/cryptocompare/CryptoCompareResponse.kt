package com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare


import com.google.gson.annotations.SerializedName
import com.katilijiwo.ministockbitapp.base.BaseResponse

class CryptoCompareResponse: BaseResponse() {
    @SerializedName("Data")
    lateinit var `data`: List<Data>

    @SerializedName("HasWarning")
    var hasWarning: Boolean = false

    @SerializedName("Message")
    lateinit var message: String

    @SerializedName("MetaData")
    lateinit var metaData: MetaData

    @SerializedName("RateLimit")
    lateinit var rateLimit: RateLimit

    @SerializedName("SponsoredData")
    lateinit var sponsoredData: List<Any>

    @SerializedName("Type")
    var type: Int = 0
}