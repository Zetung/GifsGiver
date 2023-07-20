package com.zetung.gifsgiver.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.GifsGiverApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor (private val gifsGiverApi: GifsGiverApi): ViewModel() {


    var favorites = MutableLiveData<MutableList<GifModel>>().apply {
        CoroutineScope(Dispatchers.Main).launch{
            value = gifsGiverApi.getAllFavorites()
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
    }

}