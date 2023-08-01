package com.zetung.gifsgiver.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.LoadState
import com.zetung.gifsgiver.util.di.GifsSingleton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val gifsGiverApi: GifsGiverApi,
                                         private val gifsSingleton: GifsSingleton): ViewModel() {

    var loadState = MutableLiveData<LoadState>()

    var gifs = MutableLiveData<MutableList<GifModel>>().apply {
        loadGif()
    }

    fun loadGif(){
        CoroutineScope(Dispatchers.Main).launch {
            loadState.value = gifsGiverApi.getState()
            gifs.value = gifsGiverApi.loadGifs().last()
            loadState.value = gifsGiverApi.getState()
        }
    }

    fun getAllLocalGifs(){
        gifs.value = gifsSingleton.allGifs
    }

    fun setLike(gifModel: GifModel){
        CoroutineScope(Dispatchers.Main).launch {
            gifsGiverApi.addToFavorite(gifModel.id,gifModel.url)
            if (gifsGiverApi.getState() !is LoadState.Error) {
                if (gifModel in gifsSingleton.allGifs)
                    gifsSingleton.allGifs[gifsSingleton.allGifs.indexOf(gifModel)].like = true
                gifsSingleton.favoritesGifs.add(gifModel)
                gifs.value = gifsSingleton.allGifs
            }
        }
    }

    fun deleteLike(gifModel: GifModel){
        CoroutineScope(Dispatchers.Main).launch {
            gifsGiverApi.deleteFromFavorite(gifModel.id)
            if (gifsGiverApi.getState() !is LoadState.Error){
                if (gifModel in gifsSingleton.allGifs)
                    gifsSingleton.allGifs[gifsSingleton.allGifs.indexOf(gifModel)].like = false
                gifsSingleton.favoritesGifs.remove(gifModel)
                gifs.value = gifsSingleton.allGifs
            }
        }
    }
}