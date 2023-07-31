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

@RunWith(AndroidJUnit4::class)
class RetrofitConnectTest{

    private lateinit var context: Context

    @Mock private lateinit var connectorChecker: ConnectorChecker

    private lateinit var retrofitConnect: RetrofitConnect

    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
        connectorChecker = ConnectorChecker(context)
        retrofitConnect = RetrofitConnect(connectorChecker)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load gifs from internet`() = runTest{
        val result = retrofitConnect.loadGif()
        assertTrue(result.isNotEmpty())
    }

}