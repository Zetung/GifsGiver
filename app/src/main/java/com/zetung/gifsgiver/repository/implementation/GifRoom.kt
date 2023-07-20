package com.zetung.gifsgiver.repository.implementation

import android.content.Context
import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.LocalDb
import com.zetung.gifsgiver.repository.model.GifModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GifRoom @Inject constructor (private val context:Context): GifDbApi {
    override fun addToFavorite(id: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            LocalDb.getDb(context).getFavoritesDAO().addToFavorite(GifModel(id,url,true))
        }

    }

    override fun deleteFromFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            LocalDb.getDb(context).getFavoritesDAO().deleteFromFavorites(id)
        }
    }

    override suspend fun getAllFavorites(): MutableList<GifModel> {
        return LocalDb.getDb(context).getFavoritesDAO().getFavorites()
    }

    override suspend fun getAllFavoritesID(): MutableList<String> {
        return LocalDb.getDb(context).getFavoritesDAO().getAllID()
    }
}