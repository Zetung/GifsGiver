package com.zetung.gifsgiver.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zetung.gifsgiver.repository.model.GifModel

@Dao
interface GifDAO {
    @Insert
    suspend fun addToFavorite(gifModel: GifModel)
    @Query("SELECT * FROM gifs")
    suspend fun getFavorites(): MutableList<GifModel>
    @Query("DELETE FROM gifs WHERE id=:id")
    suspend fun deleteFromFavorites(id: String)
}