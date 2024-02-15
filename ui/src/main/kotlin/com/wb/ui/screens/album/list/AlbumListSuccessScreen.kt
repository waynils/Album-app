package com.wb.ui.screens.album.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.wb.ui.compose.atoms.AlbumPictureData
import com.wb.ui.compose.molecules.cells.AlbumCell
import com.wb.ui.compose.molecules.cells.AlbumCellData
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews
import com.wb.ui.utils.toUIString
import kotlinx.coroutines.flow.flowOf



@Composable
internal fun AlbumListSuccessScreen(
    albumPagingItems: LazyPagingItems<AlbumCellData>,
    onAlbumAction: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            count = albumPagingItems.itemCount,
            key = albumPagingItems.itemKey { it.id },
        ) { index ->

            albumPagingItems[index]?.let { albumCellData ->
                AlbumCell(
                    data = albumCellData,
                    onAlbumAction = onAlbumAction,
                    modifier = Modifier
                )
            }
        }
        item {
            if (albumPagingItems.loadState.append is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }

    }
}


@LightDarkModeOneDevicePreviews
@Composable
private fun AlbumListSuccessScreenPreview() {
    MyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            val dummyItems = flowOf(
                PagingData.from(
                    listOf(
                        generateAlbumCellData(1),
                        generateAlbumCellData(2),
                        generateAlbumCellData(3)
                    )
                )
            )
            AlbumListSuccessScreen(
                albumPagingItems = dummyItems.collectAsLazyPagingItems(),
                onAlbumAction = {/*Preview*/}
            )
        }
    }
}

private fun generateAlbumCellData(id: Int) = AlbumCellData(
    id = id,
    title = "John".toUIString(),
    rank = "Rank 1".toUIString(),
    albumPictureData = AlbumPictureData(urlPicture = "")
)