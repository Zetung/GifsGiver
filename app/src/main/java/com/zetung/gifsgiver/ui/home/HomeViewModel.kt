package com.zetung.gifsgiver.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zetung.gifsgiver.repository.model.AllGifs
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

//    @Inject
//    lateinit var gifsSingleton: GifsSingleton
    var loadState = MutableLiveData<LoadState>().apply {
        value = LoadState.NotStarted()
    }

    var gifs = MutableLiveData<MutableList<GifModel>>().apply {
        loadGif()
    }

    fun loadGif(){
        CoroutineScope(Dispatchers.Main).launch {
            loadState.value = LoadState.Loading()
            gifs.value = gifsGiverApi.loadGifs().last()
            loadState.value = LoadState.Done()
        }
    }

    fun getAllLocalGifs(){
        gifs.value = gifsSingleton.allGifs
    }

    fun setLike(gifModel: GifModel){
        gifsGiverApi.addToFavorite(gifModel.id,gifModel.url)
        gifsSingleton.allGifs[gifsSingleton.allGifs.indexOf(gifModel)].like = true
        gifs.value = gifsSingleton.allGifs
    }

    fun deleteLike(gifModel: GifModel){
        gifsGiverApi.deleteFromFavorite(gifModel.id)
        gifsSingleton.allGifs[gifsSingleton.allGifs.indexOf(gifModel)].like = false
        gifs.value = gifsSingleton.allGifs
    }
}