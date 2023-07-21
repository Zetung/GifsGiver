package com.zetung.gifsgiver.util.di

import android.util.Log
import com.zetung.gifsgiver.repository.model.GifModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifsSingleton @Inject constructor(){
    var allGifs = mutableListOf<GifModel>()
}

//
//class GifsSingleton (var allGifs: MutableList<GifModel>){
//
//}