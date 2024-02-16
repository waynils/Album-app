package com.wb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wb.data.local.dao.AlbumDao
import com.wb.data.local.entities.AlbumEntity

@Database(entities = [AlbumEntity::class], version = 1)
internal abstract class AlbumDatabase : RoomDatabase() {
    abstract val albumDao: AlbumDao
}