package com.icdid.dashboard.presentation.settings

import com.icdid.core.domain.model.SyncInterval

sealed interface SettingsAction {
    data object OnLogOutClick : SettingsAction
    data object OnSyncIntervalClick : SettingsAction
    data object OnSyncIntervalDialogDismiss : SettingsAction
    data class OnSyncIntervalDialogClick(val syncInterval: SyncInterval) : SettingsAction
    data object OnSyncDataClick : SettingsAction
    data object OnBackClick : SettingsAction
}