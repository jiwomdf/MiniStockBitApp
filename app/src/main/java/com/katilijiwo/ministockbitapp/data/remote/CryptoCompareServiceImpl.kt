package com.katilijiwo.ministockbitapp.data.remote

import com.katilijiwo.ministockbitapp.base.BaseService
import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.CryptoCompareResponse
import kotlinx.coroutines.Deferred

class CryptoCompareServiceImpl(private val api: Api): BaseService(), CryptoCompareService {

    override fun fetchCryptoCompare(page: Int) : CryptoCompareResponse {
       return execute(api.fetchCryptoCompare(page))
    }

}