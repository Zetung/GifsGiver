package com.zetung.gifsgiver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.adapter.GifsAdapter
import com.zetung.gifsgiver.api.GifApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.zetung.gifsgiver.databinding.ActivityMainBinding
import com.zetung.gifsgiver.model.AllGifs
import com.zetung.gifsgiver.model.DataObject
import com.zetung.gifsgiver.model.Gif
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.giphy.com/v1/"

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: GifsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        CoroutineScope(Dispatchers.IO).launch {
//            val gifList = gifApi.getGifs()
//            runOnUiThread {
//                binding.apply {
//                    adapter.setData(gifList.gifs)
//                }
//            }
//        }


        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val gifApi = retrofit.create(GifApi::class.java)

        gifApi.getGifs().enqueue(object : Callback<AllGifs?> {
            override fun onResponse(call: Call<AllGifs?>, response: Response<AllGifs?>) {
                val body = response.body()
                body?.let { adapter.setData(it.gifs) }
            }

            override fun onFailure(call: Call<AllGifs?>, t: Throwable) {

            }
        })

//        loadData {
//            adapter.setData(it)
//        }

//        val gifs = mutableListOf<Drawable>()
//        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif1,null)!!)
//        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif2,null)!!)
//        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif3,null)!!)
//        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif1,null)!!)
//        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif2,null)!!)
//        gifs.add(ResourcesCompat.getDrawable(resources,R.drawable.gif3,null)!!)

        val gifs = mutableListOf<DataObject>()

        adapter = GifsAdapter(this,gifs)
        binding.gifView.layoutManager = LinearLayoutManager(this)
        binding.gifView.adapter = adapter


    }


    private fun loadData(callback: (List<Gif>) -> Unit){
        thread {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

            val gifApi = retrofit.create(GifApi::class.java)
            val gifList = gifApi.getGifs()
            Handler(Looper.getMainLooper()).post{
                //callback.invoke(gifList.gifs)
            }
        }

    }
}