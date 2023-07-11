package com.zetung.gifsgiver

import com.zetung.gifsgiver.api.FavoriteDbApi
import com.zetung.gifsgiver.model.FavoritesModel

class FavoriteShared: FavoriteDbApi {
    override fun addToFavorite(id: String, url: String) {

    }

    override fun deleteFromFavorite(id: String) {

    }

    override fun checkFavorite(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllFavorites(): MutableList<FavoritesModel> {
        TODO("Not yet implemented")
    }
}