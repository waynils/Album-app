package com.wb.albumapp.album.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wb.ui.screens.album.detail.AlbumScreen

@Composable
fun AlbumRoute(
    modifier: Modifier = Modifier,
    viewModel: AlbumDetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.albumUIStateFlow.collectAsState()

    AlbumScreen(
        data = uiState.value,
        onErrorAction = {/*Todo Ticket <- put number ticket jira for review*/},
        modifier = modifier.fillMaxSize()
    )

}
