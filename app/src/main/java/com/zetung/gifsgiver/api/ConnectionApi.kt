package com.zetung.gifsgiver.api

import com.zetung.gifsgiver.model.DataObject

interface ConnectionApi {

    suspend fun loadGif():MutableList<DataObject>
}