package com.example.ict4gs.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Color8,
    onPrimary = White,
    secondary = Color1,
    onSecondary = Black,
    background = Color7,
    onBackground = White,
    surface = Color7,
    onSurface = White
)

private val DarkColors = darkColorScheme(
    primary = Color1,
    onPrimary = White,
    secondary = Color5,
    onSecondary = White,
    background = Color7,
    onBackground = White,
    surface = Color7,
    onSurface = White
)



@Composable
fun ICT4GSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
