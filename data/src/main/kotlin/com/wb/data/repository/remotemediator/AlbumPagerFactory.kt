package com.wb.data.repository.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wb.data.local.AlbumDatabase
import com.wb.data.local.RoomAlbumService
import com.wb.data.local.entities.AlbumEntity
import com.wb.data.network.RetrofitAlbumService
import com.wb.data.network.api.AlbumApi

internal class AlbumPagerFactory(
    private val albumDatabase: AlbumDatabase,
    private val albumApi: AlbumApi
) {

    @OptIn(ExperimentalPagingApi::class)
    internal fun createPager(pageSize: Int): Pager<Int, AlbumEntity> = Pager(
        config = PagingConfig(pageSize = pageSize),
        remoteMediator = AlbumRemoteMediator(
            localAlbumService = RoomAlbumService(albumDatabase),
            networkAlbumService = RetrofitAlbumService(albumApi),
        ),
        pagingSourceFactory = {
            albumDatabase.albumDao.pagingSource()
        }
    )

}