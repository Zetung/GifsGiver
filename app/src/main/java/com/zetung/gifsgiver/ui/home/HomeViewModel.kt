package com.zetung.gifsgiver.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zetung.gifsgiver.repository.implementation.GifRoom
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.GifsGiverImpl
import com.zetung.gifsgiver.util.RetrofitConnect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class HomeViewModel (application: Application): AndroidViewModel(application) {

    private val gifsGiverApi: GifsGiverApi = GifsGiverImpl()
    private val dbApi = GifRoom(application)

    var gifs = MutableLiveData<MutableList<GifModel>>().apply {
        CoroutineScope(Dispatchers.Main).launch {
            value = gifsGiverApi.loadGifs(RetrofitConnect(),dbApi).last()
        }
    }

    fun loadGif(){
        CoroutineScope(Dispatchers.Main).launch {
            gifs.value = gifsGiverApi.loadGifs(RetrofitConnect(),dbApi).last()
        }
    }

    fun setLike(id:String,url:String){
        gifsGiverApi.addToFavorite(id,url,dbApi)
    }

    fun deleteLike(id:String){
        gifsGiverApi.deleteFromFavorite(id,GifRoom(getApplication()))
    }
}