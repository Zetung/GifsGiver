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
import com.zetung.gifsgiver.util.ConnectionApi
import com.zetung.gifsgiver.repository.FavoriteDbApi
import com.zetung.gifsgiver.databinding.FragmentHomeBinding
import com.zetung.gifsgiver.repository.implementation.FavoriteRoom
import com.zetung.gifsgiver.repository.model.DataObject
import com.zetung.gifsgiver.repository.model.FavoritesModel
import com.zetung.gifsgiver.ui.OnLikeClickListener
import com.zetung.gifsgiver.ui.favorites.FavoritesViewModel

class HomeFragment : Fragment(), OnLikeClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GifsAdapter

    private lateinit var favoriteDb: FavoriteDbApi
    private lateinit var retrofitConnect: ConnectionApi


    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var retrofitViewModel: RetrofitViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //favoriteDb = FavoriteShared(con,"favorite_pref")
        favoriteDb = FavoriteRoom(requireContext())

        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        val favoritesObserver = Observer<List<FavoritesModel>> { favoritesList ->
            adapter.favoriteList = favoritesList.toMutableList()
            adapter.notifyDataSetChanged()
        }
        favoritesViewModel.favorites.observe(viewLifecycleOwner, favoritesObserver)

        retrofitViewModel = ViewModelProvider(this).get(RetrofitViewModel::class.java)
        val gifsObserver = Observer<List<DataObject>> { gifsList ->
            adapter.gifs = gifsList.toMutableList()
            adapter.notifyDataSetChanged()
        }
        retrofitViewModel.gifs.observe(viewLifecycleOwner, gifsObserver)

        adapter = GifsAdapter(requireContext(),
            retrofitViewModel.gifs.value.orEmpty().toMutableList(),
            favoriteDb,
            favoritesViewModel.favorites.value.orEmpty().toMutableList())
        adapter.setOnButtonClickListener(this)
        binding.gifView.layoutManager = LinearLayoutManager(requireContext())
        binding.gifView.adapter = adapter


        var gifs = mutableListOf<DataObject>()
        var favoriteList = mutableListOf<String>()
//        adapter = GifsAdapter(requireContext(),gifs,favoriteDb,favoriteList)
//        binding.gifView.layoutManager = LinearLayoutManager(requireContext())
//        binding.gifView.adapter = adapter

//        retrofitConnect = RetrofitConnect()
//        lifecycleScope.launch {
//            gifs = retrofitConnect.loadGif()
//            adapter.setData(gifs)
//        }
//
//        lifecycleScope.launch {
//            favoriteList = favoriteDb.getAllFavoritesID()
//            adapter.setFavorite(favoriteList)
//        }


        binding.swipeRefresh.setOnRefreshListener {
//            CoroutineScope(Dispatchers.Main).launch{
//                adapter.setData(retrofitConnect.loadGif())
//                stopProgressBarAnimation()
//            }
            retrofitViewModel.loadGif()
            stopProgressBarAnimation()
        }


        return binding.root
    }

    override fun onLikeClick(position: Int, data: FavoritesModel, isLiked: Boolean) {
        if(isLiked) {
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