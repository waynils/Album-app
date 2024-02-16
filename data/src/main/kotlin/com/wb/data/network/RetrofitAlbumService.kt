package com.wb.data.network

import com.wb.data.local.entities.AlbumEntity
import com.wb.data.mapper.toEntity
import com.wb.data.network.api.AlbumApi
import com.wb.data.utils.Clock
import com.wb.data.utils.DefaultClock
import javax.inject.Inject

internal class RetrofitAlbumService(
    private val albumApi: AlbumApi,
    private val clock: Clock = DefaultClock()
) : NetworkAlbumService {
    override suspend fun getAlbumsEntities(): List<AlbumEntity> =
        albumApi.getAlbums().map { it.toEntity(lastUpdated = clock.getCurrentTimeInMillis()) }
}