package com.zetung.gifsgiver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.adapter.GifsAdapter
import com.zetung.gifsgiver.api.GifApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.zetung.gifsgiver.databinding.ActivityMainBinding
import com.zetung.gifsgiver.model.AllGifs
import com.zetung.gifsgiver.model.DataObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.giphy.com/v1/"

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: GifsAdapter

    private lateinit var gifApi: GifApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        gifApi = retrofit.create(GifApi::class.java)

        loadData()

        val gifs = mutableListOf<DataObject>()

        adapter = GifsAdapter(this,gifs)
        binding.gifView.layoutManager = LinearLayoutManager(this)
        binding.gifView.adapter = adapter


        binding.swipeRefresh.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData(){
        gifApi.getGifs().enqueue(object : Callback<AllGifs?> {
            override fun onResponse(call: Call<AllGifs?>, response: Response<AllGifs?>) {
                if(response.isSuccessful){
                    val body = response.body()
                    body?.let { adapter.setData(it.gifs) }
                    stopProgressBarAnimation()
                } else {
                    stopProgressBarAnimation()
                }
            }

            override fun onFailure(call: Call<AllGifs?>, t: Throwable) {
                stopProgressBarAnimation()
            }
        })
    }

    private fun stopProgressBarAnimation(){
        binding.swipeRefresh.isRefreshing = false
    }

    class LoggingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            val response = chain.proceed(request)
            Log.d("Retrofit", response.toString())
            return response
        }
    }
}