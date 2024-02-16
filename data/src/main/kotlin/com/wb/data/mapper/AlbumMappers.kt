package com.wb.data.mapper

import com.wb.data.local.entities.AlbumEntity
import com.wb.data.network.model.AlbumApiResponse
import com.wb.domain.model.Album

internal fun AlbumEntity.toExposedModel() = Album(
    id = albumId,
    title = title,
    pictureUrl = pictureUrl,
    thumbnailUrl = thumbnailUrl
)

internal fun AlbumApiResponse.AlbumApiResponseItem.toEntity(lastUpdated: Long) = AlbumEntity(
    albumId = id,
    title = title,
    pictureUrl = url,
    thumbnailUrl = thumbnailUrl,
    lastUpdated = lastUpdated
)