package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.model.DataObject

interface ConnectionApi {
    suspend fun loadGif():MutableList<DataObject>
    fun getState(): LoadState
}