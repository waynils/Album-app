package com.wb.albumapp.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectivity {
    val isNetworkConnected: StateFlow<Boolean>
}