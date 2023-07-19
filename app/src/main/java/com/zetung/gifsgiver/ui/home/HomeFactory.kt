package com.zetung.gifsgiver.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zetung.gifsgiver.util.GifsGiverApi

class HomeFactory(val application: Application,val giverApi: GifsGiverApi):
    ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(application,giverApi) as T
    }
}