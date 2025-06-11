package model

import androidx.window.core.layout.WindowSizeClass

enum class DeviceType {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE;

    fun isTablet() = this == TABLET_PORTRAIT || this == TABLET_LANDSCAPE

    companion object {
        fun fromWindowsSizeClass(windowSizeClass: WindowSizeClass): DeviceType {
            return when {
                windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> {
                    return when {
                        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) -> TABLET_LANDSCAPE
                        else -> MOBILE_LANDSCAPE
                    }
                }
                windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
                    return when {
                        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND) -> TABLET_PORTRAIT
                        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND) -> TABLET_LANDSCAPE
                        else -> MOBILE_LANDSCAPE
                    }
                }
                windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) ->  MOBILE_PORTRAIT

                else -> TABLET_PORTRAIT
            }
        }
    }
}