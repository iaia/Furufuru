package dev.iaiabot.furufuru.feature.ui.issue

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColors = darkColors()
private val LightColors = lightColors()

@Composable
fun FurufuruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors =
        if (darkTheme) {
            DarkColors
        } else {
            LightColors
        }
    ) {
        content()
    }
}
