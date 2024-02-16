package com.wb.data.repository.remotemediator

import android.database.sqlite.SQLiteException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.wb.data.local.LocalAlbumService
import com.wb.data.local.entities.AlbumEntity
import com.wb.data.network.NetworkAlbumService
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalPagingApi::class)
@RunWith(MockitoJUnitRunner::class)
class AlbumRemoteMediatorTest {

    @Mock
    private lateinit var mockLocalAlbumService: LocalAlbumService

    @Mock
    private lateinit var mockNetworkAlbumService: NetworkAlbumService

    private val sut by lazy {
        AlbumRemoteMediator(mockLocalAlbumService, mockNetworkAlbumService)
    }

    @Test
    fun `Given LocalAlbumService isCacheUpToDate = false When sut call initialize Then return SKIP_INITIAL_REFRESH`() =
        runTest {
            //Given
            whenever(mockLocalAlbumService.isCacheUpToDate()).thenReturn(true)

            //When
            val result = sut.initialize()

            //Then
            assertEquals(
                expected = RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH,
                actual = result
            )
        }

    @Test
    fun `Given LocalAlbumService isCacheUpToDate = true When sut call initialize Then return LAUNCH_INITIAL_REFRESH`() =
        runTest {
            //Given
            whenever(mockLocalAlbumService.isCacheUpToDate()).thenReturn(false)

            //When
            val result = sut.initialize()

            //Then
            assertEquals(
                expected = RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH,
                actual = result
            )
        }

    @Test
    fun `Given loadType Refresh When sut load Then return MediatorResult Success`() =
        runTest {
            //Given
            val loadType = LoadType.REFRESH
            val albumEntities: List<AlbumEntity> = mock()
            val pagingState: PagingState<Int, AlbumEntity> = PagingState(
                listOf(),
                null,
                PagingConfig(10),
                10
            )
            whenever(mockNetworkAlbumService.getAlbumsEntities()).thenReturn(albumEntities)

            //When
            val result = sut.load(loadType = loadType, pagingState)

            //Then
            verify(mockNetworkAlbumService, times(1)).getAlbumsEntities()
            verify(mockLocalAlbumService, times(1)).insert(eq(LoadType.REFRESH), eq(albumEntities))
            assertIs<RemoteMediator.MediatorResult.Success>(result)
            assertTrue(result.endOfPaginationReached)
        }

    @Test
    fun `Given loadType Refresh with getAlbumsEntities return NetworkException When sut load Then return MediatorResult Error`() =
        runTest {
            //Given
            val loadType = LoadType.REFRESH
            val pagingState: PagingState<Int, AlbumEntity> = PagingState(
                listOf(),
                null,
                PagingConfig(10),
                10
            )
            whenever(mockNetworkAlbumService.getAlbumsEntities()).thenThrow(
                HttpException(
                    Response.error<Any>(
                        404,
                        "raw response body as string".toResponseBody("plain/text".toMediaTypeOrNull())
                    )
                )
            )

            //When
            val result = sut.load(loadType = loadType, pagingState)

            //Then
            verify(mockNetworkAlbumService, times(1)).getAlbumsEntities()
            verifyNoMoreInteractions(mockLocalAlbumService)
            assertIs<RemoteMediator.MediatorResult.Error>(result)
            assertIs<HttpException>(result.throwable)
        }

    @Test
    fun `Given loadType Refresh with insert return SQLiteException When sut load Then return MediatorResult Error`() =
        runTest {
            //Given
            val loadType = LoadType.REFRESH
            val albumEntities: List<AlbumEntity> = mock()
            val pagingState: PagingState<Int, AlbumEntity> = PagingState(
                listOf(),
                null,
                PagingConfig(10),
                10
            )
            whenever(mockNetworkAlbumService.getAlbumsEntities()).thenReturn(albumEntities)
            whenever(mockLocalAlbumService.insert(loadType, albumEntities)).thenThrow(
                SQLiteException()
            )

            //When
            val result = sut.load(loadType = loadType, pagingState)

            //Then
            verify(mockNetworkAlbumService, times(1)).getAlbumsEntities()
            verify(mockLocalAlbumService, times(1)).insert(eq(loadType), eq(albumEntities))
            assertIs<RemoteMediator.MediatorResult.Error>(result)
            assertIs<SQLiteException>(result.throwable)
        }

    @Test
    fun `Given loadType Prepend When sut load Then return MediatorResult Success`() =
        runTest {
            //Given
            val loadType = LoadType.PREPEND

            val pagingState: PagingState<Int, AlbumEntity> = PagingState(
                listOf(),
                null,
                PagingConfig(10),
                10
            )

            //When
            val result = sut.load(loadType = loadType, pagingState)

            //Then
            verifyNoMoreInteractions(mockNetworkAlbumService, mockLocalAlbumService)
            assertIs<RemoteMediator.MediatorResult.Success>(result)
            assertTrue(result.endOfPaginationReached)
        }

    @Test
    fun `Given loadType Append When sut load Then return MediatorResult Success`() =
        runTest {
            //Given
            val loadType = LoadType.APPEND

            val pagingState: PagingState<Int, AlbumEntity> = PagingState(
                listOf(),
                null,
                PagingConfig(10),
                10
            )

            //When
            val result = sut.load(loadType = loadType, pagingState)

            //Then
            verifyNoMoreInteractions(mockNetworkAlbumService, mockLocalAlbumService)
            assertIs<RemoteMediator.MediatorResult.Success>(result)
            assertTrue(result.endOfPaginationReached)
        }

}