package com.zetung.gifsgiver.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.repository.FavoriteDbApi
import com.zetung.gifsgiver.databinding.FragmentFavoritesBinding
import com.zetung.gifsgiver.repository.implementation.FavoriteRoom
import com.zetung.gifsgiver.repository.model.FavoritesModel
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteAdapter

    private var favoritesList = mutableListOf<FavoritesModel>()

    private lateinit var favoriteDb : FavoriteDbApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        //favoriteDb = FavoriteShared(con,"favorite_pref")
        favoriteDb = FavoriteRoom(requireContext())

        lifecycleScope.launch {
            favoritesList = favoriteDb.getAllFavorites()
            adapter = FavoriteAdapter(requireContext(),favoritesList,favoriteDb)
            binding.gifView.layoutManager = LinearLayoutManager(requireContext())
            binding.gifView.adapter = adapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}