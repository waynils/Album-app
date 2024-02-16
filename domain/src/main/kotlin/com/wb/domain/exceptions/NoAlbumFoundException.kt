package com.wb.domain.exceptions

class NoAlbumFoundException(
    detailMessage: String = "No album found in database",
    throwable: Throwable? = null
) : Exception(detailMessage, throwable)