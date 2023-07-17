package com.zetung.gifsgiver.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zetung.gifsgiver.repository.model.DataObject
import com.zetung.gifsgiver.util.RetrofitConnect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (application: Application): AndroidViewModel(application) {

    private val retrofitConnect = RetrofitConnect()

    var gifs = MutableLiveData<MutableList<DataObject>>().apply {
        CoroutineScope(Dispatchers.Main).launch {
            value = retrofitConnect.loadGif()
        }
    }

    fun loadGif(){
        CoroutineScope(Dispatchers.Main).launch {
            gifs.value = retrofitConnect.loadGif()
        }
    }
}