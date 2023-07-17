package com.zetung.gifsgiver.ui.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        //favoriteDb = FavoriteShared(con,"favorite_pref")
        //favoriteDb = FavoriteRoom(requireContext())

        val favoritesViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val favoritesObserver = Observer<List<FavoritesModel>> { favoritesList ->
            adapter.gifs = favoritesList.toMutableList()
            adapter.notifyDataSetChanged()
        }

        favoritesViewModel.favorites.observe(viewLifecycleOwner, favoritesObserver)

        adapter = FavoriteAdapter(requireContext(), favoritesViewModel.favorites.value.orEmpty().toMutableList())
        binding.gifView.layoutManager = LinearLayoutManager(requireContext())
        binding.gifView.adapter = adapter


//        lifecycleScope.launch {
//            favoritesList = favoriteDb.getAllFavorites()
//            adapter = FavoriteAdapter(requireContext(),favoritesList,favoriteDb)
//            binding.gifView.layoutManager = LinearLayoutManager(requireContext())
//            binding.gifView.adapter = adapter
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}