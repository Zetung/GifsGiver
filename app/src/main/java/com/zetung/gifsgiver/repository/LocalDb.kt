package com.zetung.gifsgiver.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zetung.gifsgiver.repository.model.GifModel

@Database(entities = [GifModel::class], version = 1)
abstract class LocalDb: RoomDatabase() {
    abstract fun getFavoritesDAO(): GifDAO
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