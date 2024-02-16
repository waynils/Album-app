package com.wb.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "albums")
internal data class AlbumEntity(
    val albumId: Int,
    val title: String,
    @ColumnInfo(name = "url_picture") val pictureUrl: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
    @ColumnInfo(name = "last_updated") val lastUpdated: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}