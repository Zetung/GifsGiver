package com.zetung.gifsgiver.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.LoadState
import com.zetung.gifsgiver.util.RetrofitConnect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val gifsGiverApi: GifsGiverApi): ViewModel() {


    var loadState = MutableLiveData<LoadState>().apply {
        value = LoadState.NotStarted()
    }

    var gifs = MutableLiveData<MutableList<GifModel>>().apply {
        loadGif()
    }

    fun loadGif(){
        CoroutineScope(Dispatchers.Main).launch {
            loadState.value = LoadState.Loading()
            gifs.value = gifsGiverApi.loadGifs(RetrofitConnect()).last()
            loadState.value = LoadState.Done()
        }
    }

    fun getAllLocalGifs(){
        CoroutineScope(Dispatchers.Main).launch {
            gifs.value = gifsGiverApi.getAllLocalGifs()
        }
    }

    fun setLike(id:String,url:String){
        gifsGiverApi.addToFavorite(id,url)
    }

    fun deleteLike(id:String){
        gifsGiverApi.deleteFromFavorite(id)
    }
}