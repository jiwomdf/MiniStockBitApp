package com.katilijiwo.ministockbitapp

import android.app.Application
import com.katilijiwo.ministockbitapp.di.networkModule
import com.katilijiwo.ministockbitapp.di.repositoryModule
import com.katilijiwo.ministockbitapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MiniStockbitApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MiniStockbitApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}