package com.wb.albumapp.album.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.wb.albumapp.MainDispatcherRule
import com.wb.domain.model.Album
import com.wb.domain.usecase.GetAlbumUseCase
import com.wb.domain.usecase.utils.UseCaseResult
import com.wb.ui.screens.album.detail.AlbumScreenData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

@RunWith(MockitoJUnitRunner::class)
class AlbumDetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Mock
    private lateinit var mockSavedStateHandle: SavedStateHandle

    @Mock
    private lateinit var mockGetAlbum: GetAlbumUseCase

    @Mock
    private lateinit var mockUIMapper: AlbumDetailUIMapper

    @Mock
    private lateinit var mockErrorData: AlbumScreenData.Error

    @Mock
    private lateinit var mockSuccessData: AlbumScreenData.Success

    private val sut by lazy {
        AlbumDetailViewModel(mockSavedStateHandle, mockGetAlbum, mockUIMapper)
    }

    @BeforeTest
    fun setUp() {
        whenever(mockUIMapper.toErrorData()).thenReturn(mockErrorData)
        whenever(mockUIMapper.toSuccessData(any())).thenReturn(mockSuccessData)
    }

    @Test
    fun `Given albumId null When sut init Then return error data`() = runTest {
        whenever(mockSavedStateHandle.get<String>(any())).thenReturn(null)
        sut.albumUIStateFlow.test {
            val result = awaitItem()
            assertIs<AlbumScreenData.Error>(result)
        }
    }

    @Test
    fun `Given albumId not int When sut init Then return error data`() = runTest {
        whenever(mockSavedStateHandle.get<String>(any())).thenReturn("hello")

        sut.albumUIStateFlow.test {
            val result = awaitItem()
            assertIs<AlbumScreenData.Error>(result)
        }
    }


    @Test
    fun `Given valid albumId and success getAlbumUseCase When sut init Then return success data`() =
        runTest {
            whenever(mockSavedStateHandle.get<String>(any())).thenReturn("1")

            val album: Album = mock()
            whenever(mockGetAlbum(1)).thenReturn(UseCaseResult.success(album))

            sut.albumUIStateFlow.test {
                val result = awaitItem()
                assertIs<AlbumScreenData.Success>(result)
            }
        }

    @Test
    fun `Given valid albumId and error getAlbumUseCase When sut init Then return success data`() =
        runTest {
            whenever(mockSavedStateHandle.get<String>(any())).thenReturn("1")

            val exception: Exception = mock()
            whenever(mockGetAlbum(1)).thenReturn(UseCaseResult.defaultFailure(exception))

            sut.albumUIStateFlow.test {
                val result = awaitItem()
                assertIs<AlbumScreenData.Error>(result)
            }
        }

}