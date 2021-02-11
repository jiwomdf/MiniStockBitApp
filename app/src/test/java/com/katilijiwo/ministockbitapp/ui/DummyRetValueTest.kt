package com.katilijiwo.ministockbitapp.ui

import com.katilijiwo.ministockbitapp.JsonToPojoConverter
import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.CryptoCompareResponse

object DummyRetValueTest {

    val PAGE_1_JSON = "page1json.json"

    inline fun <reified BASE> page_1_json(): CryptoCompareResponse {
        return JsonToPojoConverter.convertJson<BASE, CryptoCompareResponse>(PAGE_1_JSON)
    }

}