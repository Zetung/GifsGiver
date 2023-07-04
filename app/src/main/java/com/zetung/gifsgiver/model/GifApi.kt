package com.zetung.gifsgiver.model

import retrofit2.http.GET

interface GifApi {
    @GET("gifs/random?api_key=XR5yDta2h0usLLHQDcHW6LUI8nI6WtMy")
    fun getGifs(): retrofit2.Call<AllGifs>
}