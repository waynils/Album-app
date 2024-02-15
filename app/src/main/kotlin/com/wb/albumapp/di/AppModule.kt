package com.wb.albumapp.di

import android.content.Context
import com.wb.albumapp.network.DefaultNetworkConnectivity
import com.wb.albumapp.network.NetworkConnectivity
import com.wb.domain.repository.AlbumRepository
import com.wb.domain.usecase.GetAlbumUseCase
import com.wb.domain.usecase.GetAlbumsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity =
        DefaultNetworkConnectivity(context)

    @Provides
    @Singleton
    fun provideGetAlbumsUseCase(albumRepository: AlbumRepository): GetAlbumsUseCase =
        GetAlbumsUseCase(albumRepository)

    @Provides
    @Singleton
    fun provideGetAlbumUseCase(albumRepository: AlbumRepository): GetAlbumUseCase =
        GetAlbumUseCase(albumRepository)

}