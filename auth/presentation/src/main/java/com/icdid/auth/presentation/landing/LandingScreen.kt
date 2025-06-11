package com.icdid.auth.presentation.landing

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import com.icdid.core.presentation.theme.NoteMarkTheme
import model.DeviceType
import utils.MobilePreviews
import utils.TabletPreviews


@Composable
fun LandingScreen(
    onAction: (LandingAction) -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)

    val isTablet = deviceType.isTablet()

    when (deviceType) {
        DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> {
            LandingPortraitView(onAction = onAction,isTablet = isTablet)
        }

        DeviceType.TABLET_LANDSCAPE, DeviceType.MOBILE_LANDSCAPE -> {
            LandingLandscapeView(onAction = onAction, isTablet = isTablet)
        }
    }
}

@MobilePreviews
@TabletPreviews
@Composable
private fun LandingScreenPreview() {
    NoteMarkTheme {
        LandingScreen(
            onAction = {}
        )
    }
}