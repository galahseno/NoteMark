package com.icdid.core.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// region Scheme
val onSurface = Color(0xFF1B1B1C)
val opacity12 = Color(0xFF1B1B1C).copy(alpha = 0.12f)
val onSurfaceVariant = Color(0xFF535364)
val surface = Color(0xFFEFEFF2)
val surfaceLowest = Color(0xFFFFFFFF)
val error = Color(0xFFE1294B)
// end region

// region Brand
val primary = Color(0xFF5977F7)
val opacity10 = Color(0xFF5977F7)
val onPrimary = Color(0xFFFFFFFF)
val brandOpacity12 = Color(0xFFFFFFFF).copy(alpha = 0.12f)
val landingLandscape = Color(0xFFE0EAFF)
// end region

val fabBackground = Brush.linearGradient(
    colors = listOf(
        Color(0xFF58A1F8),
        Color(0xFF5A4CF7)
    ),
)