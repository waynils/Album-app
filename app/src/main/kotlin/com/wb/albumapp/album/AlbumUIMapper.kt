package com.wb.albumapp.album

import androidx.annotation.StringRes
import com.wb.ui.compose.molecules.toppappbar.TopBarAppData
import com.wb.ui.utils.UIString
import javax.inject.Inject

class AlbumUIMapper @Inject constructor() {

    fun toTopAppBarData(@StringRes messageRes: Int, hasBackButton: Boolean) = TopBarAppData(
        title = UIString.Resource(messageRes),
        hasBackButton = hasBackButton
    )
}