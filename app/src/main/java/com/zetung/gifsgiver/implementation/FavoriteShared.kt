package com.zetung.gifsgiver.implementation

import android.content.Context
import android.content.SharedPreferences
import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.model.FavoritesModel

class FavoriteShared(context: Context, nameShared: String) : FavoriteDbApi {
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

    override fun checkFavorite(id: String): Boolean {
        val localStorage = sharedPreferences.all as MutableMap<String,String>
        for (record in localStorage){
            return id == record.key
        }
        return false
    }

    override fun getAllFavorites(): MutableList<FavoritesModel> {
        val localStorage = sharedPreferences.all as MutableMap<String,String>
        val favorites = mutableListOf<FavoritesModel>()
        for (record in localStorage){
            favorites.add(FavoritesModel(record.key, record.value))
        }
        return favorites
    }

    override fun getAllFavoritesID(): MutableList<String> {
        val localStorage = sharedPreferences.all as MutableMap<String,String>
        val favorites = mutableListOf<String>()
        for (record in localStorage){
            favorites.add(record.key)
        }
        return favorites
    }
}