package com.zetung.gifsgiver.repository.implementation

import android.content.Context
import android.content.SharedPreferences
import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.model.GifModel

class GifShared(context: Context, nameShared: String) : GifDbApi {
    private val sharedPreferences : SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(nameShared,Context.MODE_PRIVATE)
    }

    override fun addToFavorite(id: String, url: String) {
        val editor = sharedPreferences.edit()
        editor.putString(id, url)
        editor.apply()
    }

    override fun deleteFromFavorite(id: String) {
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

    override suspend fun getAllFavoritesID(): MutableList<String> {
        val localStorage = sharedPreferences.all as MutableMap<String,String>
        val favorites = mutableListOf<String>()
        for (record in localStorage){
            favorites.add(record.key)
        }
        return favorites
    }
}