package com.zetung.gifsgiver.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zetung.gifsgiver.repository.model.GifModel

@Database(entities = [GifModel::class], version = 2)
abstract class LocalDb: RoomDatabase() {
    abstract fun getFavoritesDAO(): GifDAO
    companion object{
        fun getDb(context: Context): LocalDb {
            return Room.databaseBuilder(
                context.applicationContext,
                LocalDb::class.java,
                "GifsGiver.db"
            ).addMigrations(MIGRATION_1_2).build()
        }
    }
}

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `gifs` " +
                "(`id` TEXT NOT NULL, `url` TEXT NOT NULL, `like` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        database.execSQL("INSERT INTO `gifs` (`id`, `url`, `like`) SELECT `id`, `url`, 1 FROM `favorites`")
        database.execSQL("DROP TABLE IF EXISTS `favorites`")
    }

}