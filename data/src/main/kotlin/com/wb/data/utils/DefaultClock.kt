package com.wb.data.utils

internal class DefaultClock : Clock {
    override fun getCurrentTimeInMillis() = System.currentTimeMillis()
}