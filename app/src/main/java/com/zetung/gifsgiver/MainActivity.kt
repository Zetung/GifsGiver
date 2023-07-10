package com.zetung.gifsgiver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zetung.gifsgiver.adapter.GifsAdapter
import com.zetung.gifsgiver.api.GifApi
import com.zetung.gifsgiver.databinding.ActivityMainBinding
import com.zetung.gifsgiver.model.AllGifs
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

//        val navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        val navController = findNavController(R.id.fragmentContainer)
//        navigationView.setupWithNavController(navController)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigationView.setupWithNavController(navController)

//        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create()).build()
//
//        gifApi = retrofit.create(GifApi::class.java)
//
//        loadData()
//
//        val gifs = mutableListOf<DataObject>()
//
//        adapter = GifsAdapter(this,gifs)
//        binding.gifView.layoutManager = LinearLayoutManager(this)
//        binding.gifView.adapter = adapter
//
//
//        binding.swipeRefresh.setOnRefreshListener {
//            loadData()
//        }
    }

}