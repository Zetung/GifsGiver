package com.zetung.gifsgiver.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.databinding.FragmentHomeBinding
import com.zetung.gifsgiver.repository.model.DataObject
import com.zetung.gifsgiver.repository.model.FavoritesModel
import com.zetung.gifsgiver.ui.OnLikeClickListener
import com.zetung.gifsgiver.ui.favorites.FavoritesViewModel

class HomeFragment : Fragment(), OnLikeClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GifsAdapter

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        val favoritesObserver = Observer<List<FavoritesModel>> { favoritesList ->
            adapter.favoriteList = favoritesList.toMutableList()
            adapter.notifyDataSetChanged()
        }
        favoritesViewModel.favorites.observe(viewLifecycleOwner, favoritesObserver)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val gifsObserver = Observer<List<DataObject>> { gifsList ->
            adapter.gifs = gifsList.toMutableList()
            adapter.notifyDataSetChanged()
        }
        homeViewModel.gifs.observe(viewLifecycleOwner, gifsObserver)

        adapter = GifsAdapter(requireContext(),
            homeViewModel.gifs.value.orEmpty().toMutableList(),
            favoritesViewModel.favorites.value.orEmpty().toMutableList())
        adapter.setOnButtonClickListener(this)
        binding.gifView.layoutManager = LinearLayoutManager(requireContext())
        binding.gifView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            homeViewModel.loadGif()
            stopProgressBarAnimation()
        }


        return binding.root
    }

    override fun onLikeClick(position: Int, data: FavoritesModel, isLiked: Boolean) {
        if(!isLiked) {
            favoritesViewModel.deleteLike(data)
            adapter.favoriteList.remove(data)
        } else {
            favoritesViewModel.setLike(data)
            adapter.favoriteList.add(data)
        }
    }

    private fun stopProgressBarAnimation(){
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}