package com.katilijiwo.ministockbitapp.base

import android.util.Log
import com.google.gson.Gson
import com.katilijiwo.ministockbitapp.BuildConfig
import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.CryptoCompareResponse
import retrofit2.Call
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseService {
    companion object {
        fun<T: BaseResponse> execute(call: Call<T>) : T {
            try{
                val response = call.execute()
                return when(response.isSuccessful){
                    true -> {
                        if(BuildConfig.BUILD_TYPE == ("debug"))
                            Log.d("<RES>", Gson().toJson(response.body()!!))
                        response.body()!!
                    }
                    false -> {
                        if(BuildConfig.BUILD_TYPE == "debug")
                            Log.d("<RES>", response.message())
                        when(response.code()){
                            HttpURLConnection.HTTP_NOT_FOUND -> {
                                throw IOException("No Internet Connection")
                            }
                            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                                throw IOException("User Unauthorized")
                            }
                        }
                        throw HttpException(response)
                    }
                }
            }
            catch (e : Exception){
                if(BuildConfig.BUILD_TYPE == "debug")
                    e.message?.let {
                        Log.d("<RES>", it)
                    }
                when(e){
                    is SocketTimeoutException -> {
                        throw SocketTimeoutException("Connection Timed Out")
                    }
                    is UnknownHostException -> {
                        throw UnknownHostException("No Internet Connection")
                    }
                    else -> {
                        throw Exception("Some Error Occurred")
                    }
                }
            }
        }
    }

}