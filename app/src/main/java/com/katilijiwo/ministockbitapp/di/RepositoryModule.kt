package com.katilijiwo.ministockbitapp.di

import com.katilijiwo.ministockbitapp.data.Repository
import com.katilijiwo.ministockbitapp.data.RepositoryImpl
import com.katilijiwo.ministockbitapp.data.remote.CryptoCompareService
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get()) }
}

fun createRepository(
    cryptoCompareService: CryptoCompareService,
) : Repository = RepositoryImpl(cryptoCompareService)