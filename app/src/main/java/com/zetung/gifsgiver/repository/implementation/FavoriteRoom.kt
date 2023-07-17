package com.zetung.gifsgiver.repository.implementation

import android.content.Context
import com.zetung.gifsgiver.repository.FavoriteDbApi
import com.zetung.gifsgiver.repository.LocalDb
import com.zetung.gifsgiver.repository.model.FavoritesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteRoom (private val context:Context): FavoriteDbApi {
    override fun addToFavorite(id: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            LocalDb.getDb(context).getFavoritesDAO().addToFavorite(FavoritesModel(id,url))
        }

    }

    override fun deleteFromFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            LocalDb.getDb(context).getFavoritesDAO().deleteFromFavorites(id)
        }
    }

    override suspend fun checkFavorite(id: String): Boolean {
        return false
    }

    override suspend fun getAllFavorites(): MutableList<FavoritesModel> {
        return LocalDb.getDb(context).getFavoritesDAO().getFavorites()
    }

    override suspend fun getAllFavoritesID(): MutableList<String> {
        return LocalDb.getDb(context).getFavoritesDAO().getAllID()
    }
}