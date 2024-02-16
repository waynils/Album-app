package com.wb.data.network.api

import com.wb.data.network.model.AlbumApiResponse
import retrofit2.http.*


internal interface AlbumApi {
    @GET("/img/shared/technical-test.json")
    suspend fun getAlbums(): AlbumApiResponse
}
