package com.zetung.gifsgiver.repository

import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.LoadState


interface GifDbApi {
    suspend fun addToFavorite(id:String,url:String)
    suspend fun deleteFromFavorite(id:String)
    suspend fun getAllFavorites():MutableList<GifModel>
    fun getState():LoadState
}