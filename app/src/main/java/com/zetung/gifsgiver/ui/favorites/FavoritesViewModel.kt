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
        CoroutineScope(Dispatchers.Main).launch{
            //value = gifsGiverApi.getAllFavorites()
            value = gifsSingleton.getFavorites()
        }
    }

    fun loadFavorites(){
        CoroutineScope(Dispatchers.Main).launch{
            favorites.value = gifsSingleton.getFavorites()
        }
    }

    fun deleteLike(gifModel: GifModel){
        favorites.value!!.remove(gifModel)
        gifsGiverApi.deleteFromFavorite(gifModel.id)
        gifsSingleton.allGifs[gifsSingleton.allGifs.indexOf(gifModel)].like = false
    }

}