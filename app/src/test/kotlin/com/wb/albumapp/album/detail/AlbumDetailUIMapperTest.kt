package com.wb.albumapp.album.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import com.wb.albumApp.R
import com.wb.domain.model.Album
import com.wb.ui.compose.atoms.AlbumPictureData
import com.wb.ui.compose.molecules.cells.AlbumInfoCellData
import com.wb.ui.compose.molecules.errorstate.ErrorStateData
import com.wb.ui.screens.album.detail.AlbumErrorScreenData
import com.wb.ui.screens.album.detail.AlbumScreenData
import com.wb.ui.screens.album.detail.AlbumSuccessScreenData
import com.wb.ui.utils.UIString
import com.wb.ui.utils.toUIString
import kotlin.test.Test
import kotlin.test.assertEquals

class AlbumDetailUIMapperTest {

    private val sut = AlbumDetailUIMapper()

    @Test
    fun `Given Album When sut call toSuccessData Then return AlbumScreenData Success`() {
        //Given
        val album = Album(
            id = 5361,
            title = "dolores",
            pictureUrl = "https://www.google.com/#q=amet",
            thumbnailUrl = "https://www.google.com/#q=eos"
        )

        //When
        val result = sut.toSuccessData(album)

        //Then
        val expectedSuccessData = AlbumScreenData.Success(
            successData = AlbumSuccessScreenData(
                pictureData = AlbumPictureData(urlPicture = "https://www.google.com/#q=amet"),
                rankData = AlbumInfoCellData.Rank(
                    rank = UIString.Resource(
                        messageRes = R.string.rank_album,
                        args = listOf("5361")
                    ),
                    rankIcon = Icons.Default.Star
                ),
                titleData = AlbumInfoCellData.Title(
                    title = "dolores".toUIString(),
                    titleIcon = Icons.Default.Info
                )
            )
        )

        assertEquals(expected = expectedSuccessData, actual = result)
    }

    @Test
    fun `When sut call toErrorData Then return AlbumScreenData Error`() {
        //When
        val result = sut.toErrorData()

        //Then
        val expectedErrorData = AlbumScreenData.Error(
            errorData = AlbumErrorScreenData(
                errorStateData = ErrorStateData(
                    title = UIString.Resource(R.string.error_title),
                    subtitle = UIString.Resource(R.string.error_album_subtitle),
                    buttonLabel = UIString.Resource(R.string.retry),
                )
            )
        )

        assertEquals(expected = expectedErrorData, actual = result)
    }

}