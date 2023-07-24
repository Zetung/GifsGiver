package com.zetung.gifsgiver.util.di

import android.util.Log
import com.zetung.gifsgiver.repository.model.GifModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifsSingleton @Inject constructor(){
    var allGifs = mutableListOf<GifModel>()
    fun getFavorites():MutableList<GifModel>{
        val favList = mutableListOf<GifModel>()
        for (record in allGifs)
            if(record.like)
                favList.add(record)
        return favList
    }
}

//
//class GifsSingleton (var allGifs: MutableList<GifModel>){
//
//}