package com.katilijiwo.ministockbitapp.data.remote

import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.CryptoCompareResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("data/top/totaltoptiervolfull")
    fun fetchCryptoCompare(
        @Query("page") page: Int
    ) : Call<CryptoCompareResponse>
}