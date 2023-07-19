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
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.ui.OnLikeClickListener
import com.zetung.gifsgiver.ui.favorites.FavoritesViewModel
import com.zetung.gifsgiver.util.LoadState

class HomeFragment : Fragment(), OnLikeClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GifsAdapter

    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.getAllLocalGifs()
        val gifsObserver = Observer<MutableList<GifModel>> { gifsList ->
            adapter.gifs = gifsList.toMutableList()
            adapter.notifyDataSetChanged()
        }
        homeViewModel.gifs.observe(viewLifecycleOwner, gifsObserver)
        val loadObserver = Observer<LoadState> { state->
            if (state is LoadState.Done){
                homeViewModel.loadState.value = LoadState.NotStarted()
                stopProgressBarAnimation()
            }
        }
        homeViewModel.loadState.observe(viewLifecycleOwner, loadObserver)

        adapter = GifsAdapter(requireContext(),
            homeViewModel.gifs.value.orEmpty().toMutableList())
        adapter.setOnButtonClickListener(this)
        binding.gifView.layoutManager = LinearLayoutManager(requireContext())
        binding.gifView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            homeViewModel.loadGif()
        }

        return binding.root
    }

    override fun onLikeClick(position: Int, data: GifModel) {
        if(data.like) {
            homeViewModel.deleteLike(data.id)
            adapter.gifs.remove(data)
        } else {
            homeViewModel.setLike(data.id,data.url)
            adapter.gifs.add(data)
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