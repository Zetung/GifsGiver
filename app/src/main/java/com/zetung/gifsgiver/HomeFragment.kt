package com.zetung.gifsgiver

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.adapter.GifsAdapter
import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.api.GifApi
import com.zetung.gifsgiver.databinding.FragmentHomeBinding
import com.zetung.gifsgiver.implementation.FavoriteShared
import com.zetung.gifsgiver.model.AllGifs
import com.zetung.gifsgiver.model.DataObject
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private val BASE_URL = "https://api.giphy.com/v1/"

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var con: Context

    private lateinit var adapter: GifsAdapter

    private lateinit var gifApi: GifApi

    private lateinit var favoriteDb: FavoriteDbApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        con = requireContext()
        favoriteDb = FavoriteShared(con,"favorite_pref")
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        gifApi = retrofit.create(GifApi::class.java)

        loadData()


        val gifs = mutableListOf<DataObject>()
        var favoriteList = mutableListOf<String>()
        lifecycleScope.launch {
            favoriteList = favoriteDb.getAllFavoritesID()

        }
        adapter = GifsAdapter(con,gifs,favoriteDb,favoriteList)
        binding.gifView.layoutManager = LinearLayoutManager(con)
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
                    body?.let { adapter.setData(it.gifs as MutableList) }
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

}