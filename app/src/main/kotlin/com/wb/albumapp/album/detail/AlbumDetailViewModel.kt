package com.wb.albumapp.album.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wb.albumapp.album.AlbumScreenType
import com.wb.domain.model.Album
import com.wb.domain.usecase.GetAlbumUseCase
import com.wb.domain.usecase.utils.UseCaseResult
import com.wb.ui.screens.album.detail.AlbumScreenData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAlbum: GetAlbumUseCase,
    private val uiMapper: AlbumDetailUIMapper
) : ViewModel() {

    private val mutableAlbumListUIStateFlow: MutableStateFlow<AlbumScreenData> =
        MutableStateFlow(AlbumScreenData.Loading)

    val albumUIStateFlow = mutableAlbumListUIStateFlow.asStateFlow()

    private val albumId = savedStateHandle.get<String>(AlbumScreenType.ARGUMENT_ALBUM_ID)

    init {
        if (albumId == null) {
            updateUIErrorState()
        } else {
            try {
                showAlbum(id = albumId.toInt())
            } catch (e: NumberFormatException) {
                updateUIErrorState()
            }
        }

    }

    private fun showAlbum(id: Int) {
        viewModelScope.launch {
            when (val album = getAlbum(id)) {
                is UseCaseResult.Failure -> updateUIErrorState()
                is UseCaseResult.Success -> updateUISuccessState(album.value)
            }
        }
    }

    private fun updateUISuccessState(album: Album) {
        mutableAlbumListUIStateFlow.tryEmit(
            uiMapper.toSuccessData(album)
        )
    }

    private fun updateUIErrorState() {
        mutableAlbumListUIStateFlow.tryEmit(
            uiMapper.toErrorData()
        )
    }

}



