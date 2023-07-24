package com.zetung.gifsgiver.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.di.GifsSingleton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor (private val gifsGiverApi: GifsGiverApi,
                                              private val gifsSingleton: GifsSingleton): ViewModel() {


    var favorites = MutableLiveData<MutableList<GifModel>>().apply {
        loadFavorites()
    }

    fun loadFavorites(){
        CoroutineScope(Dispatchers.Main).launch{
            favorites.value = gifsSingleton.favoritesGifs
        }
    }

    fun deleteLike(gifModel: GifModel){
        favorites.value!!.remove(gifModel)
        gifsGiverApi.deleteFromFavorite(gifModel.id)
        if (gifModel in gifsSingleton.allGifs)
            gifsSingleton.allGifs[gifsSingleton.allGifs.indexOf(gifModel)].like = false
        gifsSingleton.favoritesGifs.remove(gifModel)
    }

}