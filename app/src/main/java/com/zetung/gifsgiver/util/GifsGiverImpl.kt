package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.model.DataObject
import com.zetung.gifsgiver.repository.model.GifModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

class GifsGiverImpl(gifDbApi: GifDbApi) : GifsGiverApi {

    private val gifDbApi : GifDbApi
    private var allGifs = mutableListOf<GifModel>()

    private var internetState: LoadState = LoadState.NotStarted()
    private var localState: LoadState = LoadState.NotStarted()
    var loadState: LoadState = LoadState.NotStarted()

    init {
        this.gifDbApi = gifDbApi
    }

    private fun fetchDataFromInternet(connectorApi: ConnectionApi):
            Flow<MutableList<DataObject>> = flow {
        internetState = LoadState.Loading()
        val internetGifs = connectorApi.loadGif()
        internetState = LoadState.Done()
        emit(internetGifs)
    }

    private fun fetchDataFromDatabase(): Flow<List<String>> = flow {
        localState = LoadState.Loading()
        val localGifs = gifDbApi.getAllFavoritesID()
        localState = LoadState.Done()
        emit(localGifs)
    }

    private fun fetchDataCombined(connectorApi: ConnectionApi):
            Flow<MutableList<GifModel>> = flow {
        val deferredInternet = CoroutineScope(Dispatchers.Main).async { fetchDataFromInternet(connectorApi) }
        val deferredDatabase = CoroutineScope(Dispatchers.Main).async { fetchDataFromDatabase() }

        val fromInternet = deferredInternet.await()
        val fromDatabase = deferredDatabase.await()

        val tempGifs = mutableListOf<GifModel>()
        for(record in fromInternet.last())
            if(record.id in fromDatabase.last())
                tempGifs.add(GifModel(record.id,record.images.gif.url,true))
            else
                tempGifs.add(GifModel(record.id,record.images.gif.url,false))

        allGifs = tempGifs
        emit(allGifs)
    }


    override fun loadGifs(connectorApi: ConnectionApi): Flow<MutableList<GifModel>> {
        return fetchDataCombined(connectorApi)
    }

    override fun addToFavorite(id: String, url:String) {
        gifDbApi.addToFavorite(id,url)
    }

    override fun deleteFromFavorite(id: String) {
        gifDbApi.deleteFromFavorite(id)
    }

    override suspend fun getAllFavorites(): MutableList<GifModel> {
        return gifDbApi.getAllFavorites()
    }

    override suspend fun getAllLocalGifs():MutableList<GifModel>{
        val deferredDatabase = CoroutineScope(Dispatchers.Main).async { fetchDataFromDatabase() }
        val fromDatabase = deferredDatabase.await()
        for(record in allGifs)
            allGifs[allGifs.indexOf(record)].like = record.id in fromDatabase.last()
        return allGifs
    }
}