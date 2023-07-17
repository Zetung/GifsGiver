package com.zetung.gifsgiver.repository.model

import com.google.gson.annotations.SerializedName

data class AllGifs(
    @SerializedName("data") val gifs: List<DataObject>
)

data class DataObject (
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: DataGif
)

data class DataGif (
    @SerializedName("original") val gif: Gif
)

data class Gif(
    val url: String
)