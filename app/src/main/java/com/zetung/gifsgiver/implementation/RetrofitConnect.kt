package com.zetung.gifsgiver.implementation

import com.zetung.gifsgiver.LoggingInterceptor
import com.zetung.gifsgiver.api.GifApi
import com.zetung.gifsgiver.model.DataObject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnect {
    private val BASE_URL = "https://api.giphy.com/v1/"
    private var gifApi: GifApi

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        gifApi = retrofit.create(GifApi::class.java)
    }

    suspend fun loadGif(): MutableList<DataObject> {
        return gifApi.getGifs().gifs as MutableList<DataObject>
    }
}