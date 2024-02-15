package com.wb.ui.compose.molecules.cells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.wb.ui.compose.molecules.card.CardApp
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews
import com.wb.ui.utils.UIString
import com.wb.ui.utils.toUIString

sealed class AlbumInfoCellData(val info: UIString, val icon: ImageVector) {

    data class Title(val title: UIString, val titleIcon: ImageVector = Icons.Default.Info) :
        AlbumInfoCellData(info = title, icon = titleIcon)

    data class Rank(val rank: UIString, val rankIcon: ImageVector = Icons.Default.Star) :
        AlbumInfoCellData(info = rank, icon = rankIcon)

}

@Composable
fun AlbumInfoCell(data: AlbumInfoCellData, modifier: Modifier) {

    CardApp(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = data.info.buildString(),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = data.icon,
                contentDescription = data.info.buildString(),
                tint = MaterialTheme.colors.onBackground,
            )
        }
    }

}

@LightDarkModeOneDevicePreviews
@Composable
private fun InfoCellPreview() {
    MyAppTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(modifier = Modifier) {
                AlbumInfoCell(
                    data = AlbumInfoCellData.Title(
                        title = "Album title".toUIString(),
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                AlbumInfoCell(
                    data = AlbumInfoCellData.Rank(
                        rank = "Rank 1".toUIString(),
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}