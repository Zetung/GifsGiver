package com.zetung.gifsgiver.implementation

import android.util.Log
import com.zetung.gifsgiver.api.GifApi
import com.zetung.gifsgiver.model.AllGifs
import com.zetung.gifsgiver.model.DataObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class RetrofitConnect {
    private val BASE_URL = "https://api.giphy.com/v1/"
    private var gifApi: GifApi

    init {
//        val client = OkHttpClient.Builder()
//            .addInterceptor(LoggingInterceptor())
//            .build()
//
//        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
//            .addConverterFactory(GsonConverterFactory.create()).build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        gifApi = retrofit.create(GifApi::class.java)


    }

    suspend fun loadGif(): MutableList<DataObject> {
        return gifApi.getGifs().gifs as MutableList<DataObject>


//        try {
//            val res = gifApi.getGifs().gifs as MutableList<DataObject>
//            return res
//        } catch (e:Exception){
//            Log.d("Ret",e.message.toString())
//            return mutableListOf()
//        }


//        var result = mutableListOf<DataObject>()
//        gifApi.getGifs().enqueue(object : Callback<AllGifs?> {
//            override fun onResponse(call: Call<AllGifs?>, response: Response<AllGifs?>) {
//                if (response.isSuccessful) {
//                    val body = response.body()
//                    result = body?.gifs as MutableList<DataObject>
//                    return result
//                }
//            }
//            override fun onFailure(call: Call<AllGifs?>, t: Throwable) {
//            }
//        })
    }
}