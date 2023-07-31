package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.implementation.GifRoom
import com.zetung.gifsgiver.util.di.GifsSingleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock

class GifsGiverImplTest {

    @Mock private lateinit var connectorApi: RetrofitConnect
    @Mock private lateinit var gifDbApi: GifRoom
    @Mock private lateinit var gifsSingleton: GifsSingleton

    private lateinit var gifsGiverImpl: GifsGiverImpl

    @Before
    fun setUp() {
        gifsGiverImpl = GifsGiverImpl(connectorApi, gifDbApi, gifsSingleton)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load gifs on start or refresh home screen`() = runTest{
        val result = gifsGiverImpl.loadGifs().last()
        advanceUntilIdle()
        assertTrue(result.isNotEmpty())
    }

}