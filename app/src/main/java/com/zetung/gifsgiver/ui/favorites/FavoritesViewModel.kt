package com.zetung.gifsgiver.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.LoadState
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
        value = gifsSingleton.favoritesGifs
    }

    fun loadFavorites(){
        favorites.value = gifsSingleton.favoritesGifs
    }

    fun deleteLike(gifModel: GifModel){
        CoroutineScope(Dispatchers.Main).launch {
            gifsGiverApi.deleteFromFavorite(gifModel.id)
            if (gifsGiverApi.getState() !is LoadState.Error){
                favorites.value!!.remove(gifModel)
                if (gifModel in gifsSingleton.allGifs)
                    gifsSingleton.allGifs[gifsSingleton.allGifs.indexOf(gifModel)].like = false
                gifsSingleton.favoritesGifs.remove(gifModel)
            }
        }
    }

}