package com.zetung.gifsgiver.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zetung.gifsgiver.model.FavoritesModel

@Dao
interface FavoritesDAO {
    @Insert
    fun addToFavorite(favoritesModel: FavoritesModel)
    @Query("SELECT * FROM favorites")
    fun getFavorites(): MutableList<FavoritesModel>
    @Query("DELETE FROM favorites WHERE id=:id")
    fun deleteFromFavorites(id: String)
}