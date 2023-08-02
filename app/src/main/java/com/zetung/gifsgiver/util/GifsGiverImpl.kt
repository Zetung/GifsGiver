package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.model.DataObject
import com.zetung.gifsgiver.repository.model.GifModel
import com.zetung.gifsgiver.util.di.GifsSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class GifsGiverImpl @Inject constructor (
    private val connectorApi: ConnectionApi,
    private val gifDbApi: GifDbApi,
    private var gifsSingleton: GifsSingleton) : GifsGiverApi {

    private var loadState: LoadState = LoadState.NotStarted()

    private fun fetchDataFromInternet():
            Flow<MutableList<DataObject>> = flow {
        val internetGifs = connectorApi.loadGif()
        if(loadState !is LoadState.Error)
            loadState = connectorApi.getState()
        emit(internetGifs)
    }

    private fun fetchDataFromDatabase(): Flow<List<GifModel>> = flow {
        val localGifs = gifDbApi.getAllFavorites()
        if(loadState !is LoadState.Error)
            loadState = gifDbApi.getState()
        gifsSingleton.favoritesGifs = localGifs
        emit(localGifs)
    }

    private fun fetchDataCombined():
            Flow<MutableList<GifModel>> = flow {

        loadState = LoadState.Loading()
        val deferredInternet = CoroutineScope(Dispatchers.IO).async { fetchDataFromInternet() }
        val deferredDatabase = CoroutineScope(Dispatchers.IO).async { fetchDataFromDatabase() }

        val fromInternet = deferredInternet.await()
        val fromDatabase = deferredDatabase.await()

        val tempGifs = mutableListOf<GifModel>()
        val tempFavoritesID = mutableListOf<String>()

        if (loadState !is LoadState.Error){
            for (recordLocal in fromDatabase.last())
                tempFavoritesID.add(recordLocal.id)

            for(record in fromInternet.last())
                if(record.id in tempFavoritesID)
                    tempGifs.add(GifModel(record.id,record.images.gif.url,true))
                else
                    tempGifs.add(GifModel(record.id,record.images.gif.url,false))
        }

        gifsSingleton.allGifs = tempGifs
        emit(gifsSingleton.allGifs)
    }


    override fun loadGifs(): Flow<MutableList<GifModel>> {
        loadState = LoadState.NotStarted()
        return fetchDataCombined()
    }

    override suspend fun addToFavorite(id: String, url:String) {
        gifDbApi.addToFavorite(id,url)
        loadState = gifDbApi.getState()
    }

    override suspend fun deleteFromFavorite(id: String) {
        gifDbApi.deleteFromFavorite(id)
        loadState = gifDbApi.getState()
    }

    override fun getState(): LoadState {
        return loadState
    }
}