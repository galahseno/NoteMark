package com.icdid.auth.presentation.landing

import android.app.Activity
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePreviews
import com.icdid.core.presentation.utils.TabletPreviews

@Composable
fun LandingScreen(
    onAction: (LandingAction) -> Unit
) {
    val view = LocalView.current

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)

    val isTablet = deviceType.isTablet()

    when (deviceType) {
        DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> {
            LandingPortraitView(
                onAction = onAction,
                isTablet = isTablet,
            )
        }

        DeviceType.TABLET_LANDSCAPE, DeviceType.MOBILE_LANDSCAPE -> {
            LandingLandscapeView(
                onAction = onAction,
                isTablet = isTablet,
            )
        }
    }

    SideEffect {
        val window = (view.context as? Activity)?.window
        if(!view.isInEditMode &&  window != null) {
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
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