package com.wb.data.network

import com.wb.data.network.api.AlbumApi
import com.wb.data.network.model.AlbumApiResponse
import com.wb.data.utils.Clock
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class RetrofitAlbumServiceTest {

    @Mock
    private lateinit var mockAlbumApi: AlbumApi

    @Mock
    private lateinit var mockClock: Clock

    private val sut by lazy {
        RetrofitAlbumService(mockAlbumApi, mockClock)
    }

    private val albumResponse1: AlbumApiResponse.AlbumApiResponseItem =
        generateAlbumApiResponseItem(1)
    private val albumResponse2: AlbumApiResponse.AlbumApiResponseItem =
        generateAlbumApiResponseItem(2)
    private val albumApiResponse = AlbumApiResponse().also {
        it.addAll(listOf(albumResponse1, albumResponse2))
    }

    @BeforeTest
    fun setUp() = runTest {

        whenever(mockClock.getCurrentTimeInMillis()).thenReturn(100)
        whenever(
            mockAlbumApi.getAlbums()
        ).thenReturn(albumApiResponse)

    }

    @Test
    fun `When sut call get getAlbumEntities Then return 2 albums entity`() =
        runTest {

            //When
            val result = sut.getAlbumsEntities()

            //Then
            assertEquals(expected = 2, actual = result.size)
            assertEquals(expected = 1, actual = result[0].albumId)
            assertEquals(expected = 2, actual = result[1].albumId)
            assertEquals(expected = 100, actual = result[0].lastUpdated)
            assertEquals(expected = 100, actual = result[1].lastUpdated)
        }

    private fun generateAlbumApiResponseItem(id: Int) = AlbumApiResponse.AlbumApiResponseItem(
        id = id,
        thumbnailUrl = "https://www.google.com/#q=reprimique",
        title = "justo",
        url = "http://www.bing.com/search?q=necessitatibus"
    )
}