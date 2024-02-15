package com.wb.ui.screens.album.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wb.component.R
import com.wb.ui.compose.molecules.errorstate.ErrorStateData
import com.wb.ui.utils.UIString

sealed interface AlbumScreenData {

    data object Loading : AlbumScreenData

    data class Success(val successData: AlbumSuccessScreenData) : AlbumScreenData

    data class Error(
        val errorData: AlbumErrorScreenData = AlbumErrorScreenData(
            errorStateData = ErrorStateData(
                title = UIString.Resource(R.string.error_title),
                subtitle = UIString.Resource(R.string.error_album_subtitle),
                buttonLabel = UIString.Resource(R.string.retry),
            )
        )
    ) : AlbumScreenData
}

@Composable
fun AlbumScreen(
    data: AlbumScreenData,
    onErrorAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (data) {
        is AlbumScreenData.Error -> AlbumErrorScreen(
            data = data.errorData,
            onErrorAction = onErrorAction,
            modifier = modifier
        )

        AlbumScreenData.Loading -> AlbumLoadingScreen(modifier = modifier)
        is AlbumScreenData.Success -> AlbumSuccessScreen(
            data = data.successData,
            modifier = modifier
        )
    }
}