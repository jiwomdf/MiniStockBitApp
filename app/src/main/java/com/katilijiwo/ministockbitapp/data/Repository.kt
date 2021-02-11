package com.katilijiwo.ministockbitapp.data

import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.CryptoCompareResponse
import kotlinx.coroutines.Deferred

interface Repository {
    suspend fun fetchCryptoCompare(page: Int): Deferred<CryptoCompareResponse>
}