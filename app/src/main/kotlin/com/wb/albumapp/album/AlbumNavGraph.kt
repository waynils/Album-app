package com.wb.albumapp.album

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.wb.albumapp.album.detail.buildNavGraphAlbumRoute
import com.wb.albumapp.album.list.buildNavGraphAlbumListRoute

@Composable
fun AlbumNavGraph(
    navController: NavHostController,
    navigateToDetail: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    NavHost(navController, startDestination = AlbumScreenType.List.route) {
        buildNavGraphAlbumListRoute(
            navigateToDetail = navigateToDetail,
            navGraphBuilder = this,
            modifier = modifier
        )
        buildNavGraphAlbumRoute(navGraphBuilder = this, modifier = modifier)
    }
}