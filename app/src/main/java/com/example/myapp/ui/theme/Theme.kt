package com.example.myapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AtelierPrimary,
    secondary = AtelierSecondary,
    tertiary = AtelierAccent,
    background = AtelierBackground,
    surface = AtelierSurface,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = AtelierOnBackground,
    onSurface = AtelierOnBackground,
    secondaryContainer = AtelierAccent,
    onSecondaryContainer = Color.White
)

@Composable
fun MyAPPTheme(
    darkTheme: Boolean = true, // Force dark theme for Atelier aesthetic
    dynamicColor: Boolean = false, // Disable dynamic color to keep brand consistency
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // Always use Atelier dark scheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}