package com.zetung.gifsgiver.ui.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.databinding.FragmentFavoritesBinding
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.ui.OnLikeClickListener

class FavoritesFragment : Fragment(), OnLikeClickListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteAdapter

    private lateinit var favoritesViewModel: FavoritesViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val favoritesObserver = Observer<List<GifModel>> { favoritesList ->
            adapter.gifs = favoritesList.toMutableList()
            adapter.notifyDataSetChanged()
        }

        favoritesViewModel.favorites.observe(viewLifecycleOwner, favoritesObserver)

        adapter = FavoriteAdapter(requireContext(), favoritesViewModel.favorites.value.orEmpty().toMutableList())
        adapter.setOnButtonClickListener(this)
        binding.gifView.layoutManager = LinearLayoutManager(requireContext())
        binding.gifView.adapter = adapter

        return binding.root
    }
    override fun onLikeClick(position: Int, data: GifModel) {
        if(data.like) {
            favoritesViewModel.deleteLike(data)
            adapter.gifs.remove(data)
            adapter.notifyItemRemoved(position)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}