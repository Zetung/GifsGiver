package com.zetung.gifsgiver.api

import com.zetung.gifsgiver.model.AllGifs
import retrofit2.http.GET

interface GifApi {
    @GET("gifs/trending?api_key=XR5yDta2h0usLLHQDcHW6LUI8nI6WtMy&limit=25&offset=0&rating=g&bundle=messaging_non_clips")
    suspend fun getGifs(): AllGifs
}