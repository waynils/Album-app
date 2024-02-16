package com.wb.domain.repository

import androidx.paging.PagingData
import com.wb.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    fun getAlbumPaging(pageSize: Int): Flow<PagingData<Album>>

    suspend fun getAlbum(id: Int): Result<Album>
}