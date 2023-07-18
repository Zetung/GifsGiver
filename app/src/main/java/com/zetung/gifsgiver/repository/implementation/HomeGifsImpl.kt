package com.zetung.gifsgiver.repository.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zetung.gifsgiver.repository.FavoriteDbApi
import com.zetung.gifsgiver.repository.HomeGifsApi
import com.zetung.gifsgiver.repository.LocalDb
import com.zetung.gifsgiver.repository.model.DataObject
import com.zetung.gifsgiver.repository.model.FavoritesModel
import com.zetung.gifsgiver.repository.model.HomeGifsModel
import com.zetung.gifsgiver.util.ConnectionApi
import com.zetung.gifsgiver.util.LoadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeGifsImpl : HomeGifsApi {
    private var allGifs = mutableListOf<HomeGifsModel>()

    override fun loadGifs(connectorApi: ConnectionApi, favoriteDbApi: FavoriteDbApi): LoadState {
        var internetState: LoadState = LoadState.Loading()
        var internetGifs = mutableListOf<DataObject>()
        CoroutineScope(Dispatchers.Main).launch {
            internetGifs = connectorApi.loadGif()
            internetState = LoadState.Done()
        }

        var localState: LoadState = LoadState.Loading()
        var localGifs = mutableListOf<FavoritesModel>()
        CoroutineScope(Dispatchers.Main).launch{
            localGifs = favoriteDbApi.getAllFavorites()
            localState = LoadState.Done()
        }

        if(internetState is LoadState.Done && localState is LoadState.Done){
            for(record in internetGifs)
                for(rec in localGifs)
                    if(record.id == rec.id)
                        allGifs.add(HomeGifsModel(record.id,record.images.gif.url,true))
                    else
                        allGifs.add(HomeGifsModel(record.id,record.images.gif.url,false))

        }
        return internetState
    }

    override fun getAllGifs(): MutableList<HomeGifsModel> {
        return allGifs
    }

    override fun addToFavorite(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(id: String) {
        TODO("Not yet implemented")
    }

    override fun getAllFavorites(): MutableList<HomeGifsModel> {
        TODO("Not yet implemented")
    }
}