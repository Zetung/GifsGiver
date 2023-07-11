package com.zetung.gifsgiver

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zetung.gifsgiver.adapter.FavoriteAdapter
import com.zetung.gifsgiver.adapter.GifsAdapter
import com.zetung.gifsgiver.api.LocalDb
import com.zetung.gifsgiver.databinding.FragmentFavoritesBinding
import com.zetung.gifsgiver.model.DataGif
import com.zetung.gifsgiver.model.DataObject
import com.zetung.gifsgiver.model.FavoritesModel
import com.zetung.gifsgiver.model.Gif
import java.sql.SQLException
import kotlin.concurrent.thread

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var con: Context

    private lateinit var adapter: FavoriteAdapter

    private var favoritesList = mutableListOf<FavoritesModel>()

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

        loadFavorites {
            if(it)
                adapter.setData(favoritesList)
        }
        adapter = FavoriteAdapter(con,favoritesList)
//        val sharedPreferences = con.getSharedPreferences("like_prefs", Context.MODE_PRIVATE)
//        val localStorage = sharedPreferences.all as MutableMap<String,String>
//        val gifs = mutableListOf<DataObject>()
//        for (record in localStorage){
//            gifs.add(DataObject(record.key, DataGif(Gif(record.value))))
//        }
//        adapter = FavoriteAdapter(con,gifs)

        binding.gifView.layoutManager = LinearLayoutManager(con)
        binding.gifView.adapter = adapter

    }

    private fun loadFavorites(callback:(Boolean)->Unit){
        thread {
            try {
                favoritesList = LocalDb.getDb(con).getFavoritesDAO().getFavorites()
                Handler(Looper.getMainLooper()).post{
                    callback.invoke(true)
                }
            } catch (e:SQLException){
                Handler(Looper.getMainLooper()).post{
                    callback.invoke(false)
                }
            }
        }
    }

}