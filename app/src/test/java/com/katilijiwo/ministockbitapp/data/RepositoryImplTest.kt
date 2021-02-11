package com.katilijiwo.ministockbitapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.katilijiwo.ministockbitapp.CoroutinesTestRule
import com.katilijiwo.ministockbitapp.data.remote.CryptoCompareService
import com.katilijiwo.ministockbitapp.ui.DummyRetValueTest
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryImplTest{

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()
    @get:Rule
    val coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()
    private val cryptoCompareService = mock(CryptoCompareService::class.java)
    private val repository = FakeRepository(cryptoCompareService)

    @Test
    fun fetchCryptoCompare() = runBlocking {
        val page = 1
        val dummyData = DummyRetValueTest.page_1_json<RepositoryImpl>()
        `when`(cryptoCompareService.fetchCryptoCompare(page)).thenReturn(dummyData)
        val response = repository.fetchCryptoCompare(page).await()
        verify(cryptoCompareService).fetchCryptoCompare(page)
        assertNotNull(dummyData)
        assertEquals(response, dummyData)
    }


}