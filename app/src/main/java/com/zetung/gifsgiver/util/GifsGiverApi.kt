package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.model.GifModel
import kotlinx.coroutines.flow.Flow

interface GifsGiverApi {
    fun loadGifs(connectorApi: ConnectionApi, gifDbApi: GifDbApi): Flow<MutableList<GifModel>>
    fun addToFavorite(id:String,url:String,gifDbApi: GifDbApi)
    fun deleteFromFavorite(id:String,gifDbApi: GifDbApi)
    fun getAllFavorites(gifDbApi: GifDbApi):MutableList<GifModel>
}