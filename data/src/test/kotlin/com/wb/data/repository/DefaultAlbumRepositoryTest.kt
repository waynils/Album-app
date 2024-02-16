package com.wb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.wb.data.local.AlbumDatabase
import com.wb.data.local.dao.AlbumDao
import com.wb.data.local.entities.AlbumEntity
import com.wb.data.repository.remotemediator.AlbumPagerFactory
import com.wb.domain.exceptions.NoAlbumFoundException
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class DefaultAlbumRepositoryTest {

    @Mock
    private lateinit var mockAlbumPagerFactory: AlbumPagerFactory

    @Mock
    private lateinit var mockAlbumDatabase: AlbumDatabase

    @Mock
    private lateinit var mockAlbumPager: Pager<Int, AlbumEntity>

    private val givenAlbumEntity = AlbumEntity(
        albumId = 7366,
        title = "purus",
        pictureUrl = "http://www.bing.com/search?q=repudiare",
        thumbnailUrl = "https://duckduckgo.com/?q=tortor",
        lastUpdated = 9929
    )

    private val expectedAlbum = com.wb.domain.model.Album(
        id = 7366,
        title = "purus",
        pictureUrl = "http://www.bing.com/search?q=repudiare",
        thumbnailUrl = "https://duckduckgo.com/?q=tortor",
    )


    private val sut by lazy {
        DefaultAlbumRepository(
            albumPagerFactory = mockAlbumPagerFactory,
            albumDatabase = mockAlbumDatabase
        )
    }

    @BeforeTest
    fun setUp() {
        whenever(mockAlbumPagerFactory.createPager(any())).thenReturn(mockAlbumPager)
    }

    @Test
    fun `When sut call getAlbumPaging Return paging data with album`() = runTest {
        //Given
        val pagingData: PagingData<AlbumEntity> = PagingData.from(listOf(givenAlbumEntity))
        whenever(mockAlbumPager.flow).thenReturn(flowOf(pagingData))

        //When
        sut.getAlbumPaging(20).asSnapshot().run {
            //Then
            val result = this.first()
            assertEquals(
                expected = expectedAlbum,
                actual = result
            )
        }
    }

    @Test
    fun `Given id When sut call getAlbum return expected Album`() = runTest {
        val albumDao: AlbumDao = mock()
        whenever(albumDao.getById(any())).thenReturn(givenAlbumEntity)

        whenever(mockAlbumDatabase.albumDao).thenReturn(albumDao)

        val result = sut.getAlbum(1)
        assertTrue(result.isSuccess)
        assertEquals(expected = expectedAlbum, actual = result.getOrNull())
    }

    @Test
    fun `Given id When sut call getAlbum return NoAlbumFoundException`() = runTest {
        val albumDao: AlbumDao = mock()
        whenever(albumDao.getById(any())).thenReturn(null)

        whenever(mockAlbumDatabase.albumDao).thenReturn(albumDao)

        val result = sut.getAlbum(1)
        assertTrue(result.isFailure)
        assertIs<NoAlbumFoundException>(result.exceptionOrNull())
    }


}
