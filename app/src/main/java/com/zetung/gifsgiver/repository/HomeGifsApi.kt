package com.zetung.gifsgiver.repository

import com.zetung.gifsgiver.repository.model.HomeGifsModel
import com.zetung.gifsgiver.util.ConnectionApi
import com.zetung.gifsgiver.util.LoadState

interface HomeGifsApi {
    fun loadGifs(connectorApi: ConnectionApi, favoriteDbApi: FavoriteDbApi): LoadState
    fun getAllGifs():List<HomeGifsModel>

    fun addToFavorite(id:String)
    fun deleteFromFavorite(id:String)
    fun getAllFavorites():MutableList<HomeGifsModel>
}