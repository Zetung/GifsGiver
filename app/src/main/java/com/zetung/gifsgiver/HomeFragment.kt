package com.zetung.gifsgiver

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.adapter.GifsAdapter
import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.databinding.FragmentHomeBinding
import com.zetung.gifsgiver.implementation.FavoriteRoom
import com.zetung.gifsgiver.implementation.RetrofitConnect
import com.zetung.gifsgiver.model.DataObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var con: Context

    private lateinit var adapter: GifsAdapter

    private lateinit var favoriteDb: FavoriteDbApi
    private val retrofitConnect = RetrofitConnect()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        con = requireContext()
        //favoriteDb = FavoriteShared(con,"favorite_pref")
        favoriteDb = FavoriteRoom(con)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        var gifs = mutableListOf<DataObject>()
        var favoriteList = mutableListOf<String>()
        adapter = GifsAdapter(con,gifs,favoriteDb,favoriteList)
        binding.gifView.layoutManager = LinearLayoutManager(con)
        binding.gifView.adapter = adapter

        lifecycleScope.launch {
            gifs = retrofitConnect.loadGif()
            adapter.setData(gifs)
        }

        lifecycleScope.launch {
            favoriteList = favoriteDb.getAllFavoritesID()
            adapter.setFavorite(favoriteList)
        }


        binding.swipeRefresh.setOnRefreshListener {
            CoroutineScope(Dispatchers.Main).launch{
                adapter.setData(retrofitConnect.loadGif())
                stopProgressBarAnimation()
            }
        }
    }

    private fun stopProgressBarAnimation(){
        binding.swipeRefresh.isRefreshing = false
    }

}