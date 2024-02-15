package com.wb.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = darkBlue,
    primaryVariant = Purple700,
    secondary = Teal200,
    onSurface = colorSnow,
    surface = darkGrey,
    background = darkerGrey,
)

private val LightColorPalette = lightColors(
    primary = lightBlue,
    primaryVariant = Purple700,
    secondary = Teal200,
    onSurface = Black200,
    surface = lightGray,
    background = white,
    onBackground = darkerGrey
)

@Composable
fun MyAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}