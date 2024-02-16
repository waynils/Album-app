package com.wb.data.local

import androidx.paging.LoadType
import androidx.room.withTransaction
import com.wb.data.local.entities.AlbumEntity
import com.wb.data.utils.Clock
import com.wb.data.utils.DefaultClock
import java.util.concurrent.TimeUnit

internal class RoomAlbumService(
    private val albumDatabase: AlbumDatabase,
    private val clock: Clock = DefaultClock()
) : LocalAlbumService {

    override suspend fun insert(loadType: LoadType, albumEntities: List<AlbumEntity>) =
        albumDatabase.withTransaction {

            if (loadType == LoadType.REFRESH) {
                albumDatabase.albumDao.clearAll()
            }

            albumDatabase.albumDao.insertAll(albumEntities)
        }


    override suspend fun isCacheUpToDate(): Boolean {
        val cacheTimeout = TimeUnit.DAYS.toMillis(1)
        albumDatabase.albumDao.lastUpdated()?.let { lastUpdated ->
            return clock.getCurrentTimeInMillis() - lastUpdated <= cacheTimeout
        }
        return false
    }

}