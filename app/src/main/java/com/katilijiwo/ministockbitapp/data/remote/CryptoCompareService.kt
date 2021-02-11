package com.katilijiwo.ministockbitapp.data.remote

import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.CryptoCompareResponse

interface CryptoCompareService {
    fun fetchCryptoCompare(page: Int): CryptoCompareResponse
}