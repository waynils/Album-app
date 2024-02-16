package com.wb.domain.usecase

import com.wb.domain.repository.AlbumRepository
import com.wb.domain.exceptions.NoAlbumFoundException
import com.wb.domain.model.Album
import com.wb.domain.usecase.utils.UseCaseResult
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


@RunWith(MockitoJUnitRunner::class)
class GetAlbumUseCaseTest {

    @Mock
    private lateinit var mockAlbumRepository: AlbumRepository

    private val album = Album(
        id = 1,
        title = "albumTitle",
        pictureUrl = "pictureUrl",
        thumbnailUrl = "thumbnailUrl",
    )

    private val sut by lazy {
        GetAlbumUseCase(mockAlbumRepository)
    }


    @BeforeTest
    fun setUp() = runTest {

        whenever(
            mockAlbumRepository.getAlbum(1)
        ).thenAnswer { Result.success(album) }

        whenever(
            mockAlbumRepository.getAlbum(2)
        ).thenReturn(Result.failure(NoAlbumFoundException()))
    }

    @Test
    fun `Given id When sut invoke Then return the album`() =
        runTest {

            //When
            val result = sut(1)

            //Then
            assertEquals(
                expected = album,
                actual = ((result as UseCaseResult.Success).value as Result<Album>).getOrNull()
            )
        }


    @Test
    fun `Given not existing id When sut invoke Then return the album`() =
        runTest {

            //When
            val result = sut(2)

            //Then
            assertIs<NoAlbumFoundException>((result as UseCaseResult.Failure).cause)
        }


}