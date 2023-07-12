package com.zetung.gifsgiver

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.adapter.FavoriteAdapter
import com.zetung.gifsgiver.adapter.GifsAdapter
import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.api.LocalDb
import com.zetung.gifsgiver.databinding.FragmentFavoritesBinding
import com.zetung.gifsgiver.implementation.FavoriteShared
import com.zetung.gifsgiver.model.DataGif
import com.zetung.gifsgiver.model.DataObject
import com.zetung.gifsgiver.model.FavoritesModel
import com.zetung.gifsgiver.model.Gif
import kotlinx.coroutines.launch
import java.sql.SQLException
import kotlin.concurrent.thread

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
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        con = requireContext()
        favoriteDb = FavoriteShared(con,"favorite_pref")
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            favoritesList = favoriteDb.getAllFavorites()
        }
        adapter = FavoriteAdapter(con,favoritesList,favoriteDb)
        binding.gifView.layoutManager = LinearLayoutManager(con)
        binding.gifView.adapter = adapter

    }

}