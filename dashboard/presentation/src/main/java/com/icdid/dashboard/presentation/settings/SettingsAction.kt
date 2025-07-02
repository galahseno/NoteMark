package com.icdid.dashboard.presentation.settings

sealed interface SettingsAction {
    data object OnLogOutClick: SettingsAction
    data object OnBackClick: SettingsAction
}