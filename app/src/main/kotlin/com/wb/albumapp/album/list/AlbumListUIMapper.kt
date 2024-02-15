package com.wb.albumapp.album.list

import com.wb.albumApp.R
import com.wb.domain.model.Album
import com.wb.ui.compose.atoms.AlbumPictureData
import com.wb.ui.compose.molecules.cells.AlbumCellData
import com.wb.ui.utils.UIString
import com.wb.ui.utils.toUIString
import javax.inject.Inject

class AlbumListUIMapper @Inject constructor() {

    fun toAlbumCellData(album: Album): AlbumCellData = AlbumCellData(
        id = album.id,
        title = album.title.toUIString(),
        rank = UIString.Resource(
            messageRes = R.string.rank_album,
            args = listOf(album.id.toString())
        ),
        albumPictureData = AlbumPictureData(album.thumbnailUrl)
    )
}