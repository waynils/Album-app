package com.wb.albumapp.album

sealed class AlbumScreenType(val route: String) {
    companion object {
        const val ARGUMENT_ALBUM_ID = "albumId"
    }

    data object List : AlbumScreenType("albumList")
    data object Detail : AlbumScreenType("album/{$ARGUMENT_ALBUM_ID}") {
        fun createRoute(id: Int) = "album/$id"
    }
}