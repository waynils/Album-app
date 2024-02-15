package com.wb.albumapp.album.detail

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wb.albumapp.album.AlbumScreenType

fun buildNavGraphAlbumRoute(
    navGraphBuilder: NavGraphBuilder,
    modifier: Modifier = Modifier
) {
    navGraphBuilder.composable(
        route = AlbumScreenType.Detail.route,
    ) {
        AlbumRoute(
            modifier = modifier
        )
    }
}