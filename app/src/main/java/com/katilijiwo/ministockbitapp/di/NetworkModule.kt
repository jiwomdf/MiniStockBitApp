package com.katilijiwo.ministockbitapp.di

import com.katilijiwo.ministockbitapp.BuildConfig
import com.katilijiwo.ministockbitapp.data.remote.Api
import com.katilijiwo.ministockbitapp.data.remote.CryptoCompareService
import com.katilijiwo.ministockbitapp.data.remote.CryptoCompareServiceImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { headerInterceptor() }
    single { okhttpClient(get()) }
    single { retrofit(get()) }
    single { apiService(get()) }
    single { createPopularMovieService(get()) }
    single { webSocketClient() }
}

fun createPopularMovieService(
    api: Api
) : CryptoCompareService = CryptoCompareServiceImpl(api)

fun apiService(
    retrofit: Retrofit
) : Api =
    retrofit.create(Api::class.java)

fun retrofit(
    okHttpClient: OkHttpClient
) : Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun okhttpClient(
    headerInterceptor: Interceptor
) : OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .build()

fun headerInterceptor() : Interceptor =
    Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url().newBuilder()
            .addQueryParameter("limit", "50")
            .addQueryParameter("tsym", "USD")
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }


fun webSocketClient(): Request {
    return Request.Builder()
        .url(BuildConfig.CRYPTO_COMPARE_WEB_SOCKET + "?api_key=" + BuildConfig.CRYPTO_COMPARE_API_KEY)
        .build()
}