package com.wb.albumapp.album.list

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wb.albumapp.album.AlbumScreenType

fun buildNavGraphAlbumListRoute(
    navGraphBuilder: NavGraphBuilder,
    navigateToDetail: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    navGraphBuilder.composable(
        route = AlbumScreenType.List.route,
    ) {
        AlbumListRoute(
            navigateToDetail = navigateToDetail,
            modifier = modifier
        )
    }
}