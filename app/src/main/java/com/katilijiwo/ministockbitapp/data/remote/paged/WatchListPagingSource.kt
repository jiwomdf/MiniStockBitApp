package com.katilijiwo.ministockbitapp.data.remote.paged

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.katilijiwo.ministockbitapp.data.Repository
import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.Data
import com.katilijiwo.ministockbitapp.util.Constant.CRYPTO_API_PER_PAGE
import com.katilijiwo.ministockbitapp.util.Constant.CRYPTO_API_STARTING_PAGE

class WatchListPagingSource (private val repository: Repository
) : PagingSource<Int, Data>() {

    private val RESPONSE_ITEMS_NULL_MSG = "response data is null"

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val startingPage = CRYPTO_API_STARTING_PAGE
        val position = params.key ?: startingPage
        val perPage = CRYPTO_API_PER_PAGE

        return try {
            val response = repository.fetchCryptoCompare(position).await()
            if(response.statusResponse == "1"){
                if(response.data != null){
                    LoadResult.Page(
                        data = response.data,
                        prevKey = if(position == startingPage) null else position - 1,
                        nextKey = if (response.data.isEmpty()) null else position + 1
                    )
                } else {
                    LoadResult.Error(NullPointerException(RESPONSE_ITEMS_NULL_MSG))
                }
            } else {
                LoadResult.Error(Exception(response.messageResponse))
            }
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition
    }
}
