package com.zetung.gifsgiver.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zetung.gifsgiver.model.FavoritesModel

@Database(entities = [FavoritesModel::class], version = 1)
abstract class LocalDb: RoomDatabase() {
    abstract fun getFavoritesDAO(): FavoritesDAO
    companion object{
        fun getDb(context: Context): LocalDb {
            return Room.databaseBuilder(
                context.applicationContext,
                LocalDb::class.java,
                "GifsGiver.db"
            ).build()
        }
    }
}