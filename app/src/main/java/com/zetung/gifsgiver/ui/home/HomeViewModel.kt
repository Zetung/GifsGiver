package com.zetung.gifsgiver.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zetung.gifsgiver.repository.implementation.GifRoom
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.GifsGiverImpl
import com.zetung.gifsgiver.util.LoadState
import com.zetung.gifsgiver.util.RetrofitConnect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class HomeViewModel (private val gifsGiverApi: GifsGiverApi): ViewModel() {


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