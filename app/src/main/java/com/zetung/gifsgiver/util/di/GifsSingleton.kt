package com.zetung.gifsgiver.util.di

import com.zetung.gifsgiver.repository.model.GifModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifsSingleton @Inject constructor(){
    var allGifs = mutableListOf<GifModel>()
    var favoritesGifs = mutableListOf<GifModel>()
}
