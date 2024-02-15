package com.wb.ui.compose.molecules.toppappbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews
import com.wb.ui.utils.UIString
import com.wb.ui.utils.toUIString

data class TopBarAppData(val title: UIString, val hasBackButton: Boolean)

@Composable
fun TopBarApp(data: TopBarAppData, onBackAction: () -> Unit, modifier: Modifier = Modifier) {

    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = modifier,
        title = {
            Text(data.title.buildString())
        },
        navigationIcon = {
            if (data.hasBackButton) {
                IconButton(onClick = onBackAction) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }else {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                    )
                }
            }
        },
    )
}

@LightDarkModeOneDevicePreviews
@Composable
fun TopBarAppPreview() {
    MyAppTheme {
        Scaffold(
            topBar = {
                TopBarApp(
                    data = TopBarAppData(title = "Hello bar".toUIString(), hasBackButton = true),
                    onBackAction = {/*Preview*/ })
            },
            content = { paddingValues ->
                paddingValues

            })

    }
}