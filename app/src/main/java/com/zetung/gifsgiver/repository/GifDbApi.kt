package com.zetung.gifsgiver.repository

import com.zetung.gifsgiver.repository.model.GifModel


interface GifDbApi {
    fun addToFavorite(id:String,url:String)
    fun deleteFromFavorite(id:String)
    suspend fun getAllFavorites():MutableList<GifModel>
    //suspend fun getAllFavoritesID():MutableList<String>
}