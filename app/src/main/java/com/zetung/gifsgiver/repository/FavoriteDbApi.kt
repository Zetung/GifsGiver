package com.zetung.gifsgiver.repository

import com.zetung.gifsgiver.repository.model.FavoritesModel

interface FavoriteDbApi {
    fun addToFavorite(id:String,url:String)
    fun deleteFromFavorite(id:String)
    suspend fun checkFavorite(id:String):Boolean
    suspend fun getAllFavorites():MutableList<FavoritesModel>
    suspend fun getAllFavoritesID():MutableList<String>
}