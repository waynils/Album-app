package com.wb.albumapp.album.list

sealed interface AlbumListUIEvent {
    data object Refresh : AlbumListUIEvent
}