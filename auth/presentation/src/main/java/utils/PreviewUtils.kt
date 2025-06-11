package utils

import androidx.compose.ui.tooling.preview.Preview

// Based on Resizable (Experimental) API 34 emulator specs
private const val MOBILE_WIDTH = 411   // 1080px ÷ (420dpi ÷ 160) = 411dp
private const val MOBILE_HEIGHT = 914  // 2400px ÷ (420dpi ÷ 160) = 914dp

private const val TABLET_WIDTH = 800  // 1200px ÷ (240dpi ÷ 160) = 800dp
private const val TABLET_HEIGHT = 1280  // 1920px ÷ (240dpi ÷ 160) = 1280dp

@Preview(
    showBackground = true,
    widthDp = MOBILE_WIDTH,
    heightDp = MOBILE_HEIGHT
)
annotation class MobilePortrait

@Preview(
    showBackground = true,
    widthDp = MOBILE_HEIGHT,
    heightDp = MOBILE_WIDTH
)
annotation class MobileLandscape

@MobilePortrait
@MobileLandscape
annotation class MobilePreviews

@Preview(
    showBackground = true,
    widthDp = TABLET_WIDTH,
    heightDp = TABLET_HEIGHT,
)
annotation class TabletPortrait

@Preview(
    showBackground = true,
    widthDp = TABLET_HEIGHT,
    heightDp = TABLET_WIDTH,
)
annotation class TabletLandscape

@TabletPortrait
@TabletLandscape
annotation class TabletPreviews