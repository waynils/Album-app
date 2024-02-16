package com.wb.data.network.model


import com.google.gson.annotations.SerializedName

class AlbumApiResponse : ArrayList<AlbumApiResponse.AlbumApiResponseItem>(){

    data class AlbumApiResponseItem(
        @SerializedName("id")
        val id: Int,
        @SerializedName("thumbnailUrl")
        val thumbnailUrl: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String
    )
}