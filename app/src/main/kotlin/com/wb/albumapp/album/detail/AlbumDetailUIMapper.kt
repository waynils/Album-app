package com.wb.albumapp.album.detail

import com.wb.albumApp.R
import com.wb.domain.model.Album
import com.wb.ui.compose.atoms.AlbumPictureData
import com.wb.ui.compose.molecules.cells.AlbumInfoCellData
import com.wb.ui.screens.album.detail.AlbumScreenData
import com.wb.ui.screens.album.detail.AlbumSuccessScreenData
import com.wb.ui.utils.UIString
import com.wb.ui.utils.toUIString
import javax.inject.Inject

class AlbumDetailUIMapper @Inject constructor() {

    fun toSuccessData(album: Album) = AlbumScreenData.Success(
        successData = AlbumSuccessScreenData(
            pictureData = AlbumPictureData(album.pictureUrl),
            rankData = AlbumInfoCellData.Rank(
                rank = UIString.Resource(
                    messageRes = R.string.rank_album,
                    args = listOf(album.id.toString())
                )
            ),
            titleData = AlbumInfoCellData.Title(title = album.title.toUIString())
        )
    )

    fun toErrorData(): AlbumScreenData.Error = AlbumScreenData.Error()
}