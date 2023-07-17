package com.zetung.gifsgiver.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.util.ConnectionApi
import com.zetung.gifsgiver.repository.FavoriteDbApi
import com.zetung.gifsgiver.databinding.FragmentHomeBinding
import com.zetung.gifsgiver.repository.implementation.FavoriteRoom
import com.zetung.gifsgiver.util.RetrofitConnect
import com.zetung.gifsgiver.repository.model.DataObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GifsAdapter

    private lateinit var favoriteDb: FavoriteDbApi
    private lateinit var retrofitConnect: ConnectionApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //favoriteDb = FavoriteShared(con,"favorite_pref")
        favoriteDb = FavoriteRoom(requireContext())

        var gifs = mutableListOf<DataObject>()
        var favoriteList = mutableListOf<String>()
        adapter = GifsAdapter(requireContext(),gifs,favoriteDb,favoriteList)
        binding.gifView.layoutManager = LinearLayoutManager(requireContext())
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

        retrofitConnect = RetrofitConnect()
        return binding.root
    }

    private fun stopProgressBarAnimation(){
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}