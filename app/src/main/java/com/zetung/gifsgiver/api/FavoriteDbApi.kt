package com.zetung.gifsgiver.api

import com.zetung.gifsgiver.model.FavoritesModel

interface FavoriteDbApi {
    fun addToFavorite(id:String,url:String)
    fun deleteFromFavorite(id:String)
    fun checkFavorite(id:String):Boolean
    fun getAllFavorites():MutableList<FavoritesModel>
}