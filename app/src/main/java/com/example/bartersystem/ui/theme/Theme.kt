package com.example.bartersystem.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BarterColorScheme = lightColorScheme(
    primary             = BrandOrange,
    onPrimary           = BrandCream,
    primaryContainer    = BrandCream,
    onPrimaryContainer  = BrandBrown,
    secondary           = BrandGreen,
    onSecondary         = BrandCream,
    secondaryContainer  = BrandGreenLight,
    onSecondaryContainer = BrandBrown,
    background          = SurfaceLight,
    onBackground        = TextPrimary,
    surface             = CardBackground,
    onSurface           = TextPrimary,
    surfaceVariant      = BrandCream,
    onSurfaceVariant    = TextSecondary,
    outline             = DividerColor,
    error               = ErrorRed,
    onError             = BrandCream
)

@Composable
fun BarterSystemTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BarterColorScheme,
        typography  = BarterTypography,
        content     = content
    )
}