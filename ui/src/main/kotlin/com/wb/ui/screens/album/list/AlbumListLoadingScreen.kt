package com.wb.ui.screens.albums.list

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
internal fun AlbumListLoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@LightDarkModeOneDevicePreviews
@Composable
private fun AlbumListLoadingScreenPreview() {
    MyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AlbumListLoadingScreen(modifier = Modifier.fillMaxSize())
        }
    }
}