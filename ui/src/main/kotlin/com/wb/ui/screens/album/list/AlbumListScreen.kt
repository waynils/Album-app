package com.wb.ui.screens.album.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.wb.component.R
import com.wb.ui.compose.molecules.cells.AlbumCellData
import com.wb.ui.compose.molecules.errorstate.ErrorStateData
import com.wb.ui.screens.albums.list.AlbumListErrorScreen
import com.wb.ui.screens.albums.list.AlbumListErrorScreenData
import com.wb.ui.screens.albums.list.AlbumListLoadingScreen
import com.wb.ui.utils.UIString

@Composable
fun AlbumListScreen(
    albumPagingItems: LazyPagingItems<AlbumCellData>,
    onErrorAction: () -> Unit,
    onAlbumAction: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val errorState = retrieveLoadStateError(albumPagingItems.loadState)
    if (errorState != null) {
        AlbumListErrorScreen(
            data = AlbumListErrorScreenData(
                errorStateData = ErrorStateData(
                    title = UIString.Resource(R.string.error_title),
                    subtitle = UIString.Resource(R.string.error_subtitle_default),
                    buttonLabel = UIString.Resource(R.string.retry),
                )
            ),
            onErrorAction = onErrorAction,
            modifier = modifier
        )
    } else {
        if (albumPagingItems.loadState.refresh is LoadState.NotLoading
            && albumPagingItems.loadState.prepend is LoadState.NotLoading
            && albumPagingItems.loadState.append is LoadState.NotLoading
        ) {
            AlbumListSuccessScreen(
                albumPagingItems = albumPagingItems,
                onAlbumAction = onAlbumAction,
                modifier = modifier
            )

        } else {
            AlbumListLoadingScreen(modifier = modifier)
        }
    }
}

private fun retrieveLoadStateError(loadStates: CombinedLoadStates): Throwable? =
    when {
        loadStates.prepend is LoadState.Error -> (loadStates.prepend as LoadState.Error).error
        loadStates.refresh is LoadState.Error -> (loadStates.refresh as LoadState.Error).error
        loadStates.append is LoadState.Error -> (loadStates.append as LoadState.Error).error
        else -> null
    }