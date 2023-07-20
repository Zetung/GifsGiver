package com.zetung.gifsgiver.ui.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zetung.gifsgiver.ui.home.HomeViewModel
import com.zetung.gifsgiver.util.GifsGiverApi

class FavoritesFactory(application: Application, val giverApi: GifsGiverApi):
    ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModel(giverApi) as T
    }
}