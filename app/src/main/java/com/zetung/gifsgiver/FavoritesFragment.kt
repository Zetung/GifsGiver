package com.zetung.gifsgiver

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.adapter.FavoriteAdapter
import com.zetung.gifsgiver.adapter.GifsAdapter
import com.zetung.gifsgiver.databinding.FragmentFavoritesBinding
import com.zetung.gifsgiver.model.DataGif
import com.zetung.gifsgiver.model.DataObject
import com.zetung.gifsgiver.model.Gif

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var con: Context

    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        con = requireContext()
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = con.getSharedPreferences("like_prefs", Context.MODE_PRIVATE)
        val localStorage = sharedPreferences.all as MutableMap<String,String>
        val gifs = mutableListOf<DataObject>()
        for (record in localStorage){
            gifs.add(DataObject(record.key, DataGif(Gif(record.value))))
        }


//        val source1 = DataObject(DataGif(Gif("https://media4.giphy.com/media/1o16YVH3PTUFQ8uskW/giphy.gif?cid=a4fb3c122btpk3zbnotr4z6hanipkvmhr8u1kvqnfcnh6jil&ep=v1_gifs_trending&rid=giphy.gif&ct=g")))
//        gifs.add(source1)
//        gifs.add(source1)
//        gifs.add(source1)

        adapter = FavoriteAdapter(con,gifs)
        binding.gifView.layoutManager = LinearLayoutManager(con)
        binding.gifView.adapter = adapter

    }


}