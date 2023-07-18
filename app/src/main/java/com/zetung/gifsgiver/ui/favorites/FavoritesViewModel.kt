package com.zetung.gifsgiver.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zetung.gifsgiver.repository.implementation.GifRoom
import com.zetung.gifsgiver.repository.model.GifModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel (application: Application): AndroidViewModel(application) {

    private val favoriteDb = GifRoom(application)

    val favorites = MutableLiveData<MutableList<GifModel>>().apply {
        CoroutineScope(Dispatchers.Main).launch{
            value = favoriteDb.getAllFavorites()
        }
    }
    fun setLike(data: GifModel){
        favorites.value!!.add(data)
        favoriteDb.addToFavorite(data.id,data.url)
    }
    fun deleteLike(data: GifModel){
        favorites.value!!.remove(data)
        favoriteDb.deleteFromFavorite(data.id)
    }

}