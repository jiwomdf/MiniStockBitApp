package com.katilijiwo.ministockbitapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.katilijiwo.ministockbitapp.util.Constant.SHARED_PREFERENCE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sharedPrefModule = module{
    single { provideSharedPref(androidApplication()) }
}

fun provideSharedPref(app: Application): SharedPreferences {
    return app.applicationContext.getSharedPreferences(
        SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )
}
