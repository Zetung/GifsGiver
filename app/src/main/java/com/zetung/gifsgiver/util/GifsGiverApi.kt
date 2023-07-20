package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.model.GifModel
import kotlinx.coroutines.flow.Flow

interface GifsGiverApi {
    fun loadGifs(connectorApi: ConnectionApi): Flow<MutableList<GifModel>>
    fun addToFavorite(id:String,url:String)
    fun deleteFromFavorite(id:String)
    suspend fun getAllFavorites():MutableList<GifModel>
    suspend fun getAllLocalGifs():MutableList<GifModel>
}