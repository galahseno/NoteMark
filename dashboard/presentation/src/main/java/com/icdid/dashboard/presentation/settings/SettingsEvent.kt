package com.icdid.dashboard.presentation.settings

import com.icdid.core.presentation.utils.UiText

sealed interface SettingsEvent {
    data class LogOutError(val error: UiText): SettingsEvent
    data object LogOutSuccess: SettingsEvent
}