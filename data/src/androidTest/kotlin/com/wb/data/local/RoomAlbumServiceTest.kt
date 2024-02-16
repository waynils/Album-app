package com.wb.data.local

import android.content.Context
import androidx.paging.LoadType
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wb.data.local.dao.AlbumDao
import com.wb.data.local.entities.AlbumEntity
import com.wb.data.utils.DefaultClock
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Spy
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class RoomAlbumServiceTest {
    private lateinit var albumDao: AlbumDao
    private lateinit var db: AlbumDatabase
    private val clock = DefaultClock()

    private val sut by lazy {
        RoomAlbumService(db, clock)
    }

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AlbumDatabase::class.java
        ).build()
        albumDao = db.albumDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun givenLoadTypeNoRefreshWithAlbum_WhenSutCallInsert_ThenAlbumInserted() = runTest {
        //Given
        val loadType = LoadType.APPEND
        val albumEntity = AlbumEntity(
            albumId = 8002,
            title = "pertinax",
            pictureUrl = "https://search.yahoo.com/search?p=habemus",
            thumbnailUrl = "https://www.google.com/#q=verear",
            lastUpdated = 8115
        )
        val albumEntities: List<AlbumEntity> = listOf(albumEntity)

        //When
        sut.insert(loadType, albumEntities)

        //Then
        val albumEntityById = albumDao.getById(8002)

        assertEquals(expected = albumEntityById, actual = albumEntity)
    }

    @Test
    @Throws(Exception::class)
    fun givenLoadTypeRefreshWithAlbum_WhenSutCallInsert_ThenAlbumInserted() = runTest {
        //Given
        val loadType = LoadType.REFRESH
        val oldAlbumEntity = AlbumEntity(
            albumId = 1,
            title = "pertinax",
            pictureUrl = "https://search.yahoo.com/search?p=habemus",
            thumbnailUrl = "https://www.google.com/#q=verear",
            lastUpdated = 8115
        )
        val newAlbumEntity = AlbumEntity(
            albumId = 2,
            title = "pertinax",
            pictureUrl = "https://search.yahoo.com/search?p=habemus",
            thumbnailUrl = "https://www.google.com/#q=verear",
            lastUpdated = 8115
        )

        val albumEntities: List<AlbumEntity> = listOf(newAlbumEntity)

        albumDao.insertAll(listOf(oldAlbumEntity))

        //When
        sut.insert(loadType, albumEntities)

        //Then
        val newAlbumEntityById = albumDao.getById(2)
        val oldAlbumEntityById = albumDao.getById(1)

        assertEquals(expected = newAlbumEntityById, actual = newAlbumEntity)
        assertEquals(expected = null, actual = oldAlbumEntityById)
    }


    @Test
    @Throws(Exception::class)
    fun givenAlbumWithCurrentTime_WhenSutCallIsCacheUpToDate_ThenReturnTrue() = runTest {
        //Given
        val albumEntity = AlbumEntity(
            albumId = 2,
            title = "pertinax",
            pictureUrl = "https://search.yahoo.com/search?p=habemus",
            thumbnailUrl = "https://www.google.com/#q=verear",
            lastUpdated = System.currentTimeMillis()
        )

        albumDao.insertAll(listOf(albumEntity))

        //When
        val result = sut.isCacheUpToDate()

        //Then
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun givenAlbumWithOldTime_WhenSutCallIsCacheUpToDate_ThenReturnFalse() = runTest {
        //Given
        val lastUpdated = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)
        val albumEntity = AlbumEntity(
            albumId = 2,
            title = "pertinax",
            pictureUrl = "https://search.yahoo.com/search?p=habemus",
            thumbnailUrl = "https://www.google.com/#q=verear",
            lastUpdated = lastUpdated
        )

        albumDao.insertAll(listOf(albumEntity))

        //When
        val result = sut.isCacheUpToDate()

        //Then
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun givenNoAlbum_WhenSutCallIsCacheUpToDate_ThenReturnFalse() = runTest {

        //When
        val result = sut.isCacheUpToDate()

        //Then
        assertFalse(result)
    }
}