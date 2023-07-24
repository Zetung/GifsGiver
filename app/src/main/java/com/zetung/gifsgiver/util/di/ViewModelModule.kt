package com.zetung.gifsgiver.util.di

import android.content.Context
import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.implementation.GifRoom
import com.zetung.gifsgiver.util.ConnectionApi
import com.zetung.gifsgiver.util.GifsGiverApi
import com.zetung.gifsgiver.util.GifsGiverImpl
import com.zetung.gifsgiver.util.RetrofitConnect
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelModule {

    @Provides
    fun provideGifsGiverApi (connectionApi: ConnectionApi,
                             gifDbApi: GifDbApi,
                             gifsSingleton: GifsSingleton):GifsGiverApi{
        return GifsGiverImpl(connectionApi, gifDbApi, gifsSingleton)
    }

    @Provides
    fun provideGifDbImpl(gifDbImpl: GifRoom): GifDbApi{
        return gifDbImpl
    }

    @Provides
    fun provideGifRoom(@ApplicationContext context: Context): GifRoom{
        return GifRoom(context)
    }

    @Provides
    fun provideConnectorImpl (retrofitConnect: RetrofitConnect): ConnectionApi{
        return retrofitConnect
    }

}