package com.wb.albumapp.album.list

import androidx.paging.PagingData
import app.cash.turbine.test
import com.wb.albumapp.MainDispatcherRule
import com.wb.albumapp.network.NetworkConnectivity
import com.wb.domain.model.Album
import com.wb.domain.usecase.GetAlbumsUseCase
import com.wb.ui.compose.molecules.cells.AlbumCellData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
class AlbumListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Mock
    private lateinit var mockNetworkConnectivity: NetworkConnectivity

    @Mock
    private lateinit var mockGetAlbumsUseCase: GetAlbumsUseCase

    @Mock
    private lateinit var mockUIMapper: AlbumListUIMapper

    private val networkConnectivityFlow = MutableStateFlow(false)

    private val albumsFlow: MutableStateFlow<PagingData<Album>> =
        MutableStateFlow(PagingData.from(listOf(mock())))

    private val sut by lazy {
        AlbumListViewModel(mockNetworkConnectivity, mockGetAlbumsUseCase, mockUIMapper)
    }

    @BeforeTest
    fun setUp() {
        whenever(mockNetworkConnectivity.isNetworkConnected).thenReturn(networkConnectivityFlow)
    }

    @Test
    fun `Given network initial false When sut init Then no event refresh sent`() = runTest {
        networkConnectivityFlow.tryEmit(false)

        sut.albumListUIEventFlow.test {

            expectNoEvents()
        }
    }

    @Test
    fun `Given network initial false and emit network true When sut init Then event refresh sent`() =
        runTest {
            networkConnectivityFlow.tryEmit(false)

            sut.albumListUIEventFlow.test {

                networkConnectivityFlow.tryEmit(true)

                val result = awaitItem()
                assertIs<AlbumListUIEvent.Refresh>(result)
            }
        }

    @Test
    fun `Given network initial true and emit network true When sut init Then no event refresh sent`() =
        runTest {

            networkConnectivityFlow.tryEmit(true)

            sut.albumListUIEventFlow.test {
                networkConnectivityFlow.tryEmit(true)

                expectNoEvents()
            }
        }

    @Test
    fun `Given useCase with paging album When sut init Then albumPagingDataFlow return a paging albumCellData `() =
        runTest {

            whenever(mockGetAlbumsUseCase.invoke(any())).thenReturn(albumsFlow)

            albumsFlow.tryEmit(PagingData.from(listOf(mock())))

            sut.albumPagingDataFlow.test {
                val result = awaitItem()
                assertIs<PagingData<AlbumCellData>>(result)

            }

        }

}
