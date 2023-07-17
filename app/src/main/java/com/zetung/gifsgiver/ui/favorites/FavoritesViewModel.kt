package com.zetung.gifsgiver.ui.favorites

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zetung.gifsgiver.repository.implementation.FavoriteRoom
import com.zetung.gifsgiver.repository.model.FavoritesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel (application: Application): AndroidViewModel(application) {

    private val favoriteDb = FavoriteRoom(application)

    val favorites = MutableLiveData<MutableList<FavoritesModel>>().apply {
        CoroutineScope(Dispatchers.Main).launch{
            value = favoriteDb.getAllFavorites()
        }
    }

}