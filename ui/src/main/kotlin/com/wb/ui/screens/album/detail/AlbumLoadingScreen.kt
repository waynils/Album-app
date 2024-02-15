package com.wb.ui.screens.album.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews

@Composable
internal fun AlbumLoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@LightDarkModeOneDevicePreviews
@Composable
private fun AlbumsLoadingScreenPreview() {
    MyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AlbumLoadingScreen(modifier = Modifier.fillMaxSize())
        }
    }
}