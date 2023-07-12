package com.zetung.gifsgiver.implementation

import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.model.FavoritesModel

class FavoriteRoom: FavoriteDbApi {
    override suspend fun addToFavorite(id: String, url: String) {

    }

    override suspend fun deleteFromFavorite(id: String) {

    }

    override suspend fun checkFavorite(id: String): Boolean {

        return false
    }

    override suspend fun getAllFavorites(): MutableList<FavoritesModel> {

        return mutableListOf()
    }

    override suspend fun getAllFavoritesID(): MutableList<String> {
        TODO("Not yet implemented")
    }
}