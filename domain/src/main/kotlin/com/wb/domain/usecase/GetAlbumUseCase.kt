package com.wb.domain.usecase

import com.wb.domain.repository.AlbumRepository
import com.wb.domain.model.Album
import com.wb.domain.usecase.utils.UseCaseResult

class GetAlbumUseCase(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(id: Int): UseCaseResult<Album, Any> {
        val album = albumRepository.getAlbum(id)

        return if (album.isFailure) {
            UseCaseResult.defaultFailure(album.exceptionOrNull()!!)
        } else {
            UseCaseResult.success(album.getOrNull()!!)
        }
    }

}


