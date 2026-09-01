package com.example.peego.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = TealPrimary,
    onPrimary = BackgroundWhite,
    background = BackgroundWhite,
    surface = BackgroundWhite,
    onSurface = TextPrimary,
    secondary = TagGreenText
)

@Composable
fun PeeGoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}
