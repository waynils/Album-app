package com.wb.ui.screens.album.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wb.ui.compose.molecules.errorstate.ErrorState
import com.wb.ui.compose.molecules.errorstate.ErrorStateData
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews
import com.wb.ui.utils.toUIString

data class AlbumErrorScreenData(val errorStateData: ErrorStateData)

@Composable
internal fun AlbumErrorScreen(
    data: AlbumErrorScreenData,
    onErrorAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        ErrorState(
            data = data.errorStateData,
            onErrorButtonAction = onErrorAction,
        )
    }
}


@LightDarkModeOneDevicePreviews
@Composable
private fun AlbumsErrorScreenPreview() {
    MyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AlbumErrorScreen(
                data = AlbumErrorScreenData(
                    ErrorStateData(
                        title = "Error".toUIString(),
                        subtitle = "an error occurred while processing your request".toUIString(),
                        buttonLabel = "Retry".toUIString(),
                    )
                ),
                onErrorAction = {/*PREVIEW*/ },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}