package com.zetung.gifsgiver.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RetrofitConnectTest{

    private lateinit var context: Context

    @Mock private lateinit var connectorChecker: ConnectorCheckerApi

    private lateinit var retrofitConnect: RetrofitConnect

    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
        connectorChecker = ConnectorCheckerImpl(context)
        retrofitConnect = RetrofitConnect(connectorChecker)
    }


    @Test
    fun `load gifs with internet`() = runTest{
        val result = retrofitConnect.loadGif()
        assertTrue(result.isNotEmpty())
        assertTrue(result.size != 0)
    }


    @Test
    fun `take state on load gifs with internet`() = runTest{
        assertTrue(retrofitConnect.getState() is LoadState.NotStarted)
        retrofitConnect.loadGif()
        assertTrue(retrofitConnect.getState() is LoadState.Done)
    }



    @Test
    fun `load gifs without internet`() = runTest{
        val connectorCheckerMock = mock(ConnectorCheckerApi::class.java)
        `when`(connectorCheckerMock.isNetworkAvailable()).thenReturn(false)

        retrofitConnect = RetrofitConnect(connectorCheckerMock)
        val result = retrofitConnect.loadGif()
        assertTrue(result.size == 0)
    }


    @Test
    fun `take state on load gifs without internet`() = runTest{
        val connectorCheckerMock = mock(ConnectorCheckerApi::class.java)
        `when`(connectorCheckerMock.isNetworkAvailable()).thenReturn(false)

        retrofitConnect = RetrofitConnect(connectorCheckerMock)
        assertTrue(retrofitConnect.getState() is LoadState.NotStarted)
        retrofitConnect.loadGif()
        assertTrue(retrofitConnect.getState() is LoadState.Error)
    }

}