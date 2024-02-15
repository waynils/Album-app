package com.wb.albumapp.album.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.wb.albumapp.network.NetworkConnectivity
import com.wb.domain.usecase.GetAlbumsUseCase
import com.wb.ui.compose.molecules.cells.AlbumCellData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    networkConnectivity: NetworkConnectivity,
    private val getAlbums: GetAlbumsUseCase,
    private val uiMapper: AlbumListUIMapper
) : ViewModel() {

    private val albumListUIEventChannel = Channel<AlbumListUIEvent>(Channel.BUFFERED)

    val albumListUIEventFlow = albumListUIEventChannel.receiveAsFlow()

    val albumPagingDataFlow: Flow<PagingData<AlbumCellData>> by lazy {
        getAlbums(pageSize = 20)
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.map { album ->
                    uiMapper.toAlbumCellData(album)
                }
            }
    }

    init {
        val initialNetworkConnectivity = networkConnectivity.isNetworkConnected.value

        networkConnectivity.isNetworkConnected
            .onEach { isNetworkConnected ->
                if (initialNetworkConnectivity != isNetworkConnected && isNetworkConnected) {
                    albumListUIEventChannel.send(AlbumListUIEvent.Refresh)
                }

            }.launchIn(scope = viewModelScope)
    }

}



