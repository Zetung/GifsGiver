package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.model.DataObject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitConnect @Inject constructor(
    private val connectorChecker: ConnectorChecker
) : ConnectionApi {
    private val BASE_URL = "https://api.giphy.com/v1/"
//    private var gifApi: GifApi
    private var loadState: LoadState = LoadState.NotStarted()

    override suspend fun loadGif(): MutableList<DataObject> {
        loadState = LoadState.Loading()
        return if (connectorChecker.isNetworkAvailable()){
            val client = OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor())
                .build()

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()

            val gifApi = retrofit.create(GifApi::class.java)

            loadState = LoadState.Done()
            gifApi.getGifs().gifs as MutableList<DataObject>
        } else {
            loadState = LoadState.Error("No network connection")
            mutableListOf()
        }
    }

    override fun getState(): LoadState {
        return loadState
    }
}