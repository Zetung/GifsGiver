package com.zetung.gifsgiver.implementation

import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.model.FavoritesModel

class FavoriteRoom: FavoriteDbApi {
    override fun addToFavorite(id: String, url: String) {

    }

    override fun deleteFromFavorite(id: String) {

    }

    override fun checkFavorite(id: String): Boolean {

        return false
    }

    override fun getAllFavorites(): MutableList<FavoritesModel> {

        return mutableListOf()
    }

    override fun getAllFavoritesID(): MutableList<String> {
        TODO("Not yet implemented")
    }
}