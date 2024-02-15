package com.wb.ui.compose.molecules.cells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wb.ui.compose.atoms.AlbumPicture
import com.wb.ui.compose.atoms.AlbumPictureData
import com.wb.ui.compose.molecules.card.CardApp
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews
import com.wb.ui.utils.UIString
import com.wb.ui.utils.toUIString

private val pictureSize = 72.dp

data class AlbumCellData(
    val id: Int,
    val title: UIString,
    val rank: UIString,
    val albumPictureData: AlbumPictureData
)

@Composable
fun AlbumCell(data: AlbumCellData, onAlbumAction: (id: Int) -> Unit, modifier: Modifier) {

    CardApp(
        onCardAction = {
            onAlbumAction(data.id)
        },
        modifier = modifier,
    ) {

        Row(
            modifier = Modifier
                .padding(end = 8.dp,)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AlbumPicture(
                    data = data.albumPictureData,
                    modifier = Modifier.size(pictureSize)
                )

                Column {

                    Text(
                        text = data.rank.buildString(),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground
                    )

                    Text(
                        text = data.title.buildString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                }

            }

            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "next",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(24.dp)
            )

        }
    }

}

@LightDarkModeOneDevicePreviews
@Composable
private fun AlbumCellPreview() {
    MyAppTheme {
        Surface(Modifier.fillMaxSize()) {
            Box(modifier = Modifier) {

                AlbumCell(
                    data = AlbumCellData(
                        id = 1,
                        title = "John album sf".toUIString(),
                        rank = "Rank : 3".toUIString(),
                        albumPictureData = AlbumPictureData(urlPicture = "")
                    ),
                    onAlbumAction = {/*Preview*/ },
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
}