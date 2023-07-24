package com.zetung.gifsgiver.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.databinding.FragmentHomeBinding
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.ui.OnLikeClickListener
import com.zetung.gifsgiver.util.LoadState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnLikeClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GifsAdapter

    private val homeViewModel: HomeViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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

        adapter = GifsAdapter(homeViewModel.gifs.value.orEmpty().toMutableList())
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
            homeViewModel.deleteLike(data)
            //adapter.gifs.remove(data)
        } else {
            homeViewModel.setLike(data)
            //adapter.gifs.add(data)
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