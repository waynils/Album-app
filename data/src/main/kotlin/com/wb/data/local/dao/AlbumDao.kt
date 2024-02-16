package com.wb.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wb.data.local.entities.AlbumEntity

@Dao
internal interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albumEntities: List<AlbumEntity>)

    @Query("SELECT * FROM albums")
    fun pagingSource(): PagingSource<Int, AlbumEntity>

    @Query("SELECT * FROM albums WHERE albumId=:id")
    suspend fun getById(id: Int): AlbumEntity?

    @Query("SELECT last_updated FROM albums LIMIT 1")
    suspend fun lastUpdated(): Long?

    @Query("DELETE FROM albums")
    suspend fun clearAll()
}