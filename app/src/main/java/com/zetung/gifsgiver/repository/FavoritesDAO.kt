package com.zetung.gifsgiver.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zetung.gifsgiver.repository.model.FavoritesModel

@Dao
interface FavoritesDAO {
    @Insert
    suspend fun addToFavorite(favoritesModel: FavoritesModel)
    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): MutableList<FavoritesModel>
    @Query("DELETE FROM favorites WHERE id=:id")
    suspend fun deleteFromFavorites(id: String)
    @Query("SELECT id FROM favorites")
    suspend fun getAllID(): MutableList<String>
}