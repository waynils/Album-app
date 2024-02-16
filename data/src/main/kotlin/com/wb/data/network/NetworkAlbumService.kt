package com.wb.data.network

import com.wb.data.local.entities.AlbumEntity

internal interface NetworkAlbumService {
    suspend fun getAlbumsEntities(): List<AlbumEntity>
}