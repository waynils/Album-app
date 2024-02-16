package com.wb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.wb.data.local.AlbumDatabase
import com.wb.data.local.entities.AlbumEntity
import com.wb.data.mapper.toExposedModel
import com.wb.domain.exceptions.NoAlbumFoundException
import com.wb.data.repository.remotemediator.AlbumPagerFactory
import com.wb.domain.repository.AlbumRepository
import com.wb.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultAlbumRepository @Inject constructor(
    private val albumPagerFactory: AlbumPagerFactory,
    private val albumDatabase: AlbumDatabase,
) : AlbumRepository {

    private lateinit var albumPager: Pager<Int, AlbumEntity>

    override fun getAlbumPaging(pageSize: Int): Flow<PagingData<Album>> {
        if (!::albumPager.isInitialized)
            albumPager = albumPagerFactory.createPager(pageSize)

        return albumPager.flow
            .map { pagingData ->
                pagingData.map { it.toExposedModel() }
            }
    }

    override suspend fun getAlbum(id: Int): Result<Album> {
        val localData = albumDatabase.albumDao.getById(id)

        return if (localData == null)
            Result.failure(NoAlbumFoundException())
        else
            Result.success(localData.toExposedModel())
    }


}