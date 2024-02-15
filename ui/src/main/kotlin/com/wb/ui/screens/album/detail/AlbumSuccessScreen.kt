package com.wb.ui.screens.album.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wb.ui.compose.atoms.AlbumPicture
import com.wb.ui.compose.atoms.AlbumPictureData
import com.wb.ui.compose.molecules.cells.AlbumInfoCell
import com.wb.ui.compose.molecules.cells.AlbumInfoCellData
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews
import com.wb.ui.utils.toUIString

private val pictureSize = 256.dp

data class AlbumSuccessScreenData(
    val pictureData: AlbumPictureData,
    val rankData: AlbumInfoCellData.Rank,
    val titleData: AlbumInfoCellData.Title,
)

@Composable
internal fun AlbumSuccessScreen(
    data: AlbumSuccessScreenData,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AlbumPicture(data = data.pictureData, modifier = Modifier.size(pictureSize))

        AlbumInfoCell(data = data.rankData, Modifier.fillMaxWidth())

        AlbumInfoCell(data = data.titleData, Modifier.fillMaxWidth())
    }
}


@LightDarkModeOneDevicePreviews
@Composable
private fun AlbumSuccessScreenPreview() {
    MyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            AlbumSuccessScreen(
                data = AlbumSuccessScreenData(
                    pictureData = AlbumPictureData(urlPicture = ""),
                    rankData = AlbumInfoCellData.Rank("rank 10 ".toUIString()),
                    titleData = AlbumInfoCellData.Title(
                        title = "super album".toUIString(),
                    ),
                )
            )
        }
    }
}
