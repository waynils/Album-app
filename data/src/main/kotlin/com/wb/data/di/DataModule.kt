package com.wb.data.di

import android.content.Context
import androidx.room.Room
import com.wb.data.local.AlbumDatabase
import com.wb.data.network.api.AlbumApi
import com.wb.data.repository.DefaultAlbumRepository
import com.wb.data.repository.remotemediator.AlbumPagerFactory
import com.wb.domain.repository.AlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://static.leboncoin.fr/"
private const val DATABASE_NAME = "album.db"

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Singleton
    @Provides
    fun provideOkHttp(
        interceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.addInterceptor(interceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()


    @Singleton
    @Provides
    fun provideAlbumApi(retrofit: Retrofit): AlbumApi = retrofit.create(AlbumApi::class.java)


    @Provides
    @Singleton
    fun provideAlbumDatabase(@ApplicationContext context: Context): AlbumDatabase =
        Room.databaseBuilder(
            context,
            AlbumDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideAlbumPagerFactory(
        albumDatabase: AlbumDatabase,
        albumApi: AlbumApi,
    ): AlbumPagerFactory = AlbumPagerFactory(
        albumDatabase = albumDatabase,
        albumApi = albumApi,
    )


    @Provides
    @Singleton
    fun provideAlbumRepository(
        albumPagerFactory: AlbumPagerFactory,
        albumDatabase: AlbumDatabase,
    ): AlbumRepository =
        DefaultAlbumRepository(
            albumPagerFactory = albumPagerFactory,
            albumDatabase = albumDatabase,
        )

}