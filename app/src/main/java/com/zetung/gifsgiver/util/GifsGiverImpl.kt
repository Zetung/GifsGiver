package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.GifDbApi
import com.zetung.gifsgiver.repository.model.DataObject
import com.zetung.gifsgiver.repository.model.GifModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class GifsGiverImpl : GifsGiverApi {

    private var internetState: LoadState = LoadState.NotStarted()
    private var localState: LoadState = LoadState.NotStarted()
    var loadState: LoadState = LoadState.NotStarted()
    private fun fetchDataFromInternet(connectorApi: ConnectionApi):
            Flow<MutableList<DataObject>> = flow {
        internetState = LoadState.Loading()
        val internetGifs = connectorApi.loadGif()
        internetState = LoadState.Done()
        emit(internetGifs)
    }

    private fun fetchDataFromDatabase(gifDbApi: GifDbApi): Flow<List<String>> = flow {
        localState = LoadState.Loading()
        val localGifs = gifDbApi.getAllFavoritesID()
        localState = LoadState.Done()
        emit(localGifs)
    }

    private fun fetchDataCombined(connectorApi: ConnectionApi, gifDbApi: GifDbApi):
            Flow<MutableList<GifModel>> = flow {
        loadState = LoadState.Loading()
        val deferredInternet = CoroutineScope(Dispatchers.Main).async { fetchDataFromInternet(connectorApi) }
        val deferredDatabase = CoroutineScope(Dispatchers.Main).async { fetchDataFromDatabase(gifDbApi) }

        val fromInternet = deferredInternet.await()
        val fromDatabase = deferredDatabase.await()

        val allGifs = mutableListOf<GifModel>()
        for(record in fromInternet.last())
            if(record.id in fromDatabase.last())
                allGifs.add(GifModel(record.id,record.images.gif.url,true))
            else
                allGifs.add(GifModel(record.id,record.images.gif.url,false))

        loadState = if(localState is LoadState.Done && internetState is LoadState.Done)
            LoadState.Done()
        else
            LoadState.Error()
        emit(allGifs)
    }


    override fun loadGifs(connectorApi: ConnectionApi, gifDbApi: GifDbApi): Flow<MutableList<GifModel>> {
        return fetchDataCombined(connectorApi,gifDbApi)
    }

    override fun addToFavorite(id: String, url:String, gifDbApi: GifDbApi) {
        gifDbApi.addToFavorite(id,url)
    }

    override fun deleteFromFavorite(id: String, gifDbApi: GifDbApi) {
        gifDbApi.deleteFromFavorite(id)
    }

    override suspend fun getAllFavorites(gifDbApi: GifDbApi): MutableList<GifModel> {
        return gifDbApi.getAllFavorites()
    }
}