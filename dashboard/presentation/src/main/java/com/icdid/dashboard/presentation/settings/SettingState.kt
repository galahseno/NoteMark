package com.icdid.dashboard.presentation.settings

import com.icdid.core.domain.model.SyncInterval
import com.icdid.core.presentation.utils.UiText
import com.icdid.dashboard.presentation.R

data class SettingState(
    val syncInterval: SyncInterval = SyncInterval.Manual,
    val isSyncIntervalDialogVisible: Boolean = false,
    val lastSync: UiText = UiText.StringResource(R.string.never_synced),
    val isLoading: Boolean = false,
    val isUnsyncedDialogVisible: Boolean = false
)
