package com.zetung.gifsgiver.repository.implementation

import android.content.Context
import android.content.SharedPreferences
import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.LoadState
import javax.inject.Inject

class GifShared @Inject constructor (context: Context) : GifDbApi {
    private val sharedPreferences : SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("gifs",Context.MODE_PRIVATE)
    }

    override suspend fun addToFavorite(id: String, url: String) {
        val editor = sharedPreferences.edit()
        editor.putString(id, url)
        editor.apply()
    }

    override suspend fun deleteFromFavorite(id: String) {
        val editor = sharedPreferences.edit()
        editor.remove(id)
        editor.apply()
    }

    override suspend fun getAllFavorites(): MutableList<GifModel> {
        val localStorage = sharedPreferences.all as MutableMap<String,String>
        val favorites = mutableListOf<GifModel>()
        for (record in localStorage){
            favorites.add(GifModel(record.key, record.value, true))
        }
        return favorites
    }

    override fun getState(): LoadState {
        TODO("Not yet implemented")
    }
}