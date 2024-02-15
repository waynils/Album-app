package com.wb.albumapp.album

import androidx.lifecycle.ViewModel
import com.wb.albumApp.R
import com.wb.ui.compose.molecules.toppappbar.TopBarAppData
import com.wb.ui.utils.UIString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumUIMapper: AlbumUIMapper
) : ViewModel() {

    private val mutableTopBarAppStateFlow: MutableStateFlow<TopBarAppData> =
        MutableStateFlow(
            TopBarAppData(
                title = UIString.Resource(R.string.title_albums),
                hasBackButton = false
            )
        )

    val topAppBarStateFlow = mutableTopBarAppStateFlow.asStateFlow()

    fun updateTopAppBarList() = mutableTopBarAppStateFlow.tryEmit(
        albumUIMapper.toTopAppBarData(R.string.title_albums, hasBackButton = false)
    )

    fun updateTopAppBarDetail() = mutableTopBarAppStateFlow.tryEmit(
        albumUIMapper.toTopAppBarData(R.string.title_album, hasBackButton = true)
    )

}



