package com.wb.data.repository.remotemediator

import android.database.sqlite.SQLiteException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.wb.data.local.LocalAlbumService
import com.wb.data.local.entities.AlbumEntity
import com.wb.data.network.NetworkAlbumService
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
internal class AlbumRemoteMediator(
    private val localAlbumService: LocalAlbumService,
    private val networkAlbumService: NetworkAlbumService
) : RemoteMediator<Int, AlbumEntity>() {

    override suspend fun initialize(): InitializeAction {

        return if (localAlbumService.isCacheUpToDate()) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AlbumEntity>,
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> {
                    val albumsFromNetwork = networkAlbumService.getAlbumsEntities()

                    localAlbumService.insert(
                        loadType = loadType,
                        albumEntities = albumsFromNetwork,
                    )

                    MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> MediatorResult.Success(endOfPaginationReached = true)
            }


        } catch (e: SQLiteException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: UnknownHostException) {
            MediatorResult.Error(e)
        }

    }
}