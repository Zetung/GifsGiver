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

class GifsGiverImpl @Inject constructor (connectorApi: ConnectionApi,
                                         gifDbApi: GifDbApi,
                                         var gifsSingleton: GifsSingleton) : GifsGiverApi {

    private val connectorApi : ConnectionApi
    private val gifDbApi : GifDbApi

//    @Inject
//    lateinit var gifsSingleton: GifsSingleton

    init {
        this.gifDbApi = gifDbApi
        this.connectorApi = connectorApi
    }

    private fun fetchDataFromInternet():
            Flow<MutableList<DataObject>> = flow {
        val internetGifs = connectorApi.loadGif()
        emit(internetGifs)
    }

    private fun fetchDataFromDatabase(): Flow<List<String>> = flow {
        val localGifs = gifDbApi.getAllFavoritesID()
        emit(localGifs)
    }

    private fun fetchDataCombined():
            Flow<MutableList<GifModel>> = flow {
        val deferredInternet = CoroutineScope(Dispatchers.Main).async { fetchDataFromInternet() }
        val deferredDatabase = CoroutineScope(Dispatchers.Main).async { fetchDataFromDatabase() }

        val fromInternet = deferredInternet.await()
        val fromDatabase = deferredDatabase.await()

        val tempGifs = mutableListOf<GifModel>()
        for(record in fromInternet.last())
            if(record.id in fromDatabase.last())
                tempGifs.add(GifModel(record.id,record.images.gif.url,true))
            else
                tempGifs.add(GifModel(record.id,record.images.gif.url,false))

        gifsSingleton.allGifs = tempGifs
        emit(gifsSingleton.allGifs)
    }


    override fun loadGifs(): Flow<MutableList<GifModel>> {
        return fetchDataCombined()
    }

    override fun addToFavorite(id: String, url:String) {
        gifDbApi.addToFavorite(id,url)
    }

    override fun deleteFromFavorite(id: String) {
        gifDbApi.deleteFromFavorite(id)
    }

//    override suspend fun getAllFavorites(): MutableList<GifModel> {
//        return gifDbApi.getAllFavorites()
//    }

//    override suspend fun getAllLocalGifs():MutableList<GifModel>{
//        val deferredDatabase = CoroutineScope(Dispatchers.Main).async { fetchDataFromDatabase() }
//        val fromDatabase = deferredDatabase.await()
//        for(record in allGifs)
//            allGifs[allGifs.indexOf(record)].like = record.id in fromDatabase.last()
//        return allGifs
//    }
}