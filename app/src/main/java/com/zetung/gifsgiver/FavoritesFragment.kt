package com.zetung.gifsgiver

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.adapter.FavoriteAdapter
import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.databinding.FragmentFavoritesBinding
import com.zetung.gifsgiver.implementation.FavoriteRoom
import com.zetung.gifsgiver.model.FavoritesModel
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var con: Context

    private lateinit var adapter: FavoriteAdapter

    private var favoritesList = mutableListOf<FavoritesModel>()

    private lateinit var favoriteDb : FavoriteDbApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        con = requireContext()
        //favoriteDb = FavoriteShared(con,"favorite_pref")
        favoriteDb = FavoriteRoom(con)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            favoritesList = favoriteDb.getAllFavorites()
            adapter = FavoriteAdapter(con,favoritesList,favoriteDb)
            binding.gifView.layoutManager = LinearLayoutManager(con)
            binding.gifView.adapter = adapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}