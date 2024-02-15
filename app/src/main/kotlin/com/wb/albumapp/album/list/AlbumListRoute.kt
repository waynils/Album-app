package com.wb.albumapp.album.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.wb.ui.compose.molecules.cells.AlbumCellData
import com.wb.ui.screens.album.list.AlbumListScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AlbumListRoute(
    navigateToDetail: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumListViewModel = hiltViewModel(),
) {
    val albumPagingItems = viewModel.albumPagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.albumListUIEventFlow.collectLatest {
            manageAlbumListScreenEvent(it, albumPagingItems)
        }
    }

    AlbumListScreen(
        onErrorAction = { albumPagingItems.retry() },
        onAlbumAction = navigateToDetail,
        albumPagingItems = albumPagingItems,
        modifier = modifier.fillMaxSize()
    )
}

private fun manageAlbumListScreenEvent(
    event: AlbumListUIEvent,
    albumPagingItems: LazyPagingItems<AlbumCellData>
) {
    when (event) {
        AlbumListUIEvent.Refresh -> albumPagingItems.refresh()
    }
}
