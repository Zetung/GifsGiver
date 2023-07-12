package com.zetung.gifsgiver.api

import com.zetung.gifsgiver.model.FavoritesModel

interface FavoriteDbApi {
    fun addToFavorite(id:String,url:String)
    fun deleteFromFavorite(id:String)
    suspend fun checkFavorite(id:String):Boolean
    suspend fun getAllFavorites():MutableList<FavoritesModel>
    suspend fun getAllFavoritesID():MutableList<String>
}