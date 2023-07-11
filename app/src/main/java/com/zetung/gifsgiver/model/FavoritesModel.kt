package com.zetung.gifsgiver.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesModel(
    @PrimaryKey
    var id:String,
    @ColumnInfo(name = "url")
    val url: String
)