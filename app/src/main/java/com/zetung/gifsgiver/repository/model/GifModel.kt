package com.zetung.gifsgiver.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gifs")
data class GifModel(
    @PrimaryKey
    var id:String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "like")
    var like: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GifModel) return false
        return id == other.id
    }
    override fun hashCode(): Int {
        return id.hashCode()
    }
}