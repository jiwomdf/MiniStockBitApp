package com.katilijiwo.ministockbitapp.ui.watchlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.*
import com.katilijiwo.ministockbitapp.CoroutineTestUtil.Companion.toDeferred
import com.katilijiwo.ministockbitapp.CoroutinesTestRule
import com.katilijiwo.ministockbitapp.data.FakeRepository
import com.katilijiwo.ministockbitapp.data.remote.json.cryptocompare.Data
import com.katilijiwo.ministockbitapp.ui.DummyRetValueTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WatchListViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()
    @get:Rule
    val coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()
    private lateinit var viewModel: WatchListViewModel
    @Mock
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun before(){
        viewModel = WatchListViewModel(fakeRepository)
    }

    // sorry.., i have tried but didn't find how to unit test Paging 3
    @Test
    fun `test observe datas from CryptoCompare API, observe succeed and data is not empty`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val observer = mock<Observer<PagingData<Data>>>()
        val data = DummyRetValueTest.page_1_json<WatchListViewModelTest>()
        `when`(fakeRepository.fetchCryptoCompare(1)).thenReturn(data.toDeferred())

        viewModel.datas.observeForever(observer)

        val result = viewModel.datas.value

        verify(fakeRepository).fetchCryptoCompare(1).toDeferred()
        assertEquals(data, result)

        val datas =  PagingData.from(data.data)
        verify(observer).onChanged(datas)

        viewModel.datas.removeObserver(observer)
    }

}