package com.wb.domain.usecase

import androidx.paging.PagingData
import com.wb.domain.model.Album
import com.wb.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow

class GetAlbumsUseCase(private val albumRepository: AlbumRepository, ) {
    operator fun invoke(pageSize: Int): Flow<PagingData<Album>> =
        albumRepository.getAlbumPaging(pageSize)
}
