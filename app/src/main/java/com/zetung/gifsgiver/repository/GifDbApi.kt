package com.zetung.gifsgiver.repository

import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.LoadState


interface GifDbApi {
    fun addToFavorite(id:String,url:String)
    fun deleteFromFavorite(id:String)
    suspend fun getAllFavorites():MutableList<GifModel>
    fun getState():LoadState
}