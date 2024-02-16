package com.wb.data.local

import androidx.paging.LoadType
import com.wb.data.local.entities.AlbumEntity

internal interface LocalAlbumService {
    suspend fun insert(loadType: LoadType, albumEntities: List<AlbumEntity>)
    suspend fun isCacheUpToDate(): Boolean
}