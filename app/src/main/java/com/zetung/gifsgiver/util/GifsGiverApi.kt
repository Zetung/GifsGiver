package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.model.GifModel
import kotlinx.coroutines.flow.Flow

interface GifsGiverApi {
    fun loadGifs(): Flow<MutableList<GifModel>>
    suspend fun addToFavorite(id:String,url:String)
    suspend fun deleteFromFavorite(id:String)
    fun getState(): LoadState
}