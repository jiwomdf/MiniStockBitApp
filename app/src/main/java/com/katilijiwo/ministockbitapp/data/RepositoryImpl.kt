package com.katilijiwo.ministockbitapp.data

import com.katilijiwo.ministockbitapp.data.remote.CryptoCompareService
import com.katilijiwo.ministockbitapp.data.remote.CryptoCompareServiceImpl
import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.CryptoCompareResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async

class RepositoryImpl(private val cryptoCompareService: CryptoCompareService): Repository {

    override suspend fun fetchCryptoCompare(page: Int): Deferred<CryptoCompareResponse> {
        return CoroutineScope(IO).async {
            lateinit var response: CryptoCompareResponse
            try {
                response = cryptoCompareService.fetchCryptoCompare(page)
                response.statusResponse = "1"
            } catch (ex: Exception) {
                response = CryptoCompareResponse()
                response.statusResponse = "-1"
                response.messageResponse = ex.message.toString()
            }
            response
        }
    }

}