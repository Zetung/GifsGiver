package com.zetung.gifsgiver.util.di

import com.zetung.gifsgiver.repository.model.GifModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object GifsSingletonModule {
//
//    @Provides
//    @Singleton
//    fun provideGifSingleton():GifsSingleton{
//        return GifsSingleton()
//    }
//
//
//}

//    @Provides
//    @Singleton
//    fun provideGifSingletonList():MutableList<GifModel>{
//        return mutableListOf()
//    }