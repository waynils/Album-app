package com.wb.albumapp.album.list

import com.wb.albumApp.R
import com.wb.domain.model.Album
import com.wb.ui.compose.atoms.AlbumPictureData
import com.wb.ui.compose.molecules.cells.AlbumCellData
import com.wb.ui.utils.UIString
import com.wb.ui.utils.toUIString
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Not necessary to Parameterized this class but ... why not :)
 */
@RunWith(Parameterized::class)
class AlbumListUIMapperTest(
    private val album: Album,
    private val expectedAlbumCellData: AlbumCellData
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}, then it maps to expected album cell data {1}")
        fun data(): Collection<Array<out Any>> = listOf(
            arrayOf(
                Album(
                    id = 5361,
                    title = "dolores",
                    pictureUrl = "https://www.google.com/#q=amet",
                    thumbnailUrl = "https://www.google.com/#q=eos"
                ),
                AlbumCellData(
                    id = 5361,
                    title = "dolores".toUIString(),
                    rank = UIString.Resource(
                        messageRes = R.string.rank_album,
                        args = listOf("5361")
                    ),
                    albumPictureData = AlbumPictureData(urlPicture = "https://www.google.com/#q=eos")
                )
            ),
            arrayOf(
                Album(
                    id = 7513,
                    title = "aenean",
                    pictureUrl = "http://www.bing.com/search?q=patrioque",
                    thumbnailUrl = "https://duckduckgo.com/?q=augue"

                ),
                AlbumCellData(
                    id = 7513,
                    title = "aenean".toUIString(),
                    rank = UIString.Resource(
                        messageRes = R.string.rank_album,
                        args = listOf("7513")
                    ),
                    albumPictureData = AlbumPictureData(urlPicture = "https://duckduckgo.com/?q=augue")
                )
            )
        )
    }

    private val sut = AlbumListUIMapper()

    @Test
    fun `Given Album`() {
        val result = sut.toAlbumCellData(album)

        assertEquals(expected = expectedAlbumCellData, actual = result)
    }

}