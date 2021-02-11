package com.katilijiwo.ministockbitapp.ui.watchlist

import androidx.lifecycle.*
import androidx.paging.*
import com.katilijiwo.ministockbitapp.data.Repository
import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.Data
import com.katilijiwo.ministockbitapp.data.remote.paged.WatchListPagingSource
import com.katilijiwo.ministockbitapp.util.AbsentLiveData
import com.katilijiwo.ministockbitapp.util.Constant

class WatchListViewModel(private val repository: Repository): ViewModel() {

    private val data = MutableLiveData<Boolean>()
    val datas = Transformations.switchMap(data){ value ->
        if(value == null){
            AbsentLiveData.create()
        } else {
            getSearchResult()
        }
    }
    fun searchCryptoCompare(refresh: Boolean = true){
        this.data.value = refresh
    }

    private fun getSearchResult(): LiveData<PagingData<Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constant.CRYPTO_API_PER_PAGE,
                maxSize = Constant.PAGER_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                WatchListPagingSource(repository)
            }
        ).liveData
    }

}