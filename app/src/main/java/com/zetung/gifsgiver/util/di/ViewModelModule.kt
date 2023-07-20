package com.zetung.gifsgiver.util.di

import android.content.Context
import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.implementation.GifRoom
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.GifsGiverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelModule {

    @Provides
    fun provideGifsGiverApi (gifDbApi: GifDbApi):GifsGiverApi{
        return GifsGiverImpl(gifDbApi)
    }

    @Provides
    fun toGifDbImpl(gifDbImpl: GifRoom): GifDbApi{
        return gifDbImpl
    }

    @Provides
    fun provideGifRoom(@ApplicationContext context: Context): GifRoom{
        return GifRoom(context)
    }

//    @Binds
//    abstract fun bindGifsGiverApi (impl: GifsGiverImpl):GifsGiverApi
//
//    @Binds
//    abstract fun bindGifDbApi(impl: GifRoom):GifDbApi



}