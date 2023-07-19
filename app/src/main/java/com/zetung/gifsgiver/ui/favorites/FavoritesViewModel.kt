package com.zetung.gifsgiver.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zetung.gifsgiver.repository.implementation.GifRoom
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.GifsGiverImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel (application: Application): AndroidViewModel(application) {

    private val gifsGiverApi: GifsGiverApi = GifsGiverImpl(GifRoom(application))

    var favorites = MutableLiveData<MutableList<GifModel>>().apply {
        CoroutineScope(Dispatchers.Main).launch{
            value = gifsGiverApi.getAllFavorites()
            //value = dbApi.getAllFavorites()
        }
    }

    fun loadFavorites(){
        CoroutineScope(Dispatchers.Main).launch{
            favorites.value = gifsGiverApi.getAllFavorites()
        }
    }

    fun deleteLike(data: GifModel){
        favorites.value!!.remove(data)
        gifsGiverApi.deleteFromFavorite(data.id)
        //dbApi.deleteFromFavorite(data.id)
    }

}