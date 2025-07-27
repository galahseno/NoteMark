package com.icdid.dashboard.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.Result
import com.icdid.core.domain.connectivity.ConnectivityObserver
import com.icdid.core.domain.model.SyncInterval
import com.icdid.core.domain.session.UserSettings
import com.icdid.core.presentation.utils.UiText
import com.icdid.core.presentation.utils.asUiText
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.util.toUiTextForLastSync
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val notesRepository: NotesRepository,
    private val userSettings: UserSettings,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    private val _event = Channel<SettingsEvent>()
    val event = _event.receiveAsFlow()

    init {
        combine(
            userSettings.getSyncInterval().distinctUntilChanged(),
            userSettings.getLastSyncTimestamp().distinctUntilChanged()
        ) { syncInterval, lastSyncTimestamp ->
            _state.update {
                it.copy(
                    syncInterval = SyncInterval.valueOf(syncInterval),
                    lastSync = lastSyncTimestamp.toUiTextForLastSync()
                )
            }
        }
            .launchIn(viewModelScope)
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnLogOutClick -> onLogOutClick()
            SettingsAction.OnManualSyncClick -> onManualSyncClick()
            SettingsAction.OnSyncIntervalClick -> onSyncIntervalClick()
            SettingsAction.OnSyncIntervalDialogDismiss -> onSyncIntervalDialogDismiss()
            is SettingsAction.OnSyncIntervalDialogClick -> onSyncIntervalDialogClick(action.syncInterval)
            SettingsAction.OnLogoutDialogConfirm -> onLogoutDialogConfirm()
            SettingsAction.OnLogoutDialogDismiss -> onLogoutDialogDismiss()
            else -> Unit
        }
    }

    private fun onLogoutDialogConfirm() {
        _state.update {
            it.copy(
                isSyncIntervalDialogVisible = false,
                isLoading = true
            )
        }
        performLogoutSync(isForceSync = true)
    }

    private fun onLogoutDialogDismiss() {
        _state.update {
            it.copy(isSyncIntervalDialogVisible = false)
        }
        performLogoutSync(isForceSync = false)
    }

    private fun onManualSyncClick() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val result = notesRepository.syncNotesManually()

            when (result) {
                is Result.Error -> _event.send(SettingsEvent.SyncError(result.error.asUiText()))
                is Result.Success -> _event.send(SettingsEvent.SyncSuccess)
            }

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun onSyncIntervalDialogClick(syncInterval: SyncInterval) {
        viewModelScope.launch {
            userSettings.setSyncInterval(syncInterval.name)
            _state.update {
                it.copy(
                    isSyncIntervalDialogVisible = false,
                    syncInterval = syncInterval
                )
            }
        }
    }

    private fun onSyncIntervalDialogDismiss() {
        _state.update {
            it.copy(
                isSyncIntervalDialogVisible = false
            )
        }
    }

    private fun onSyncIntervalClick() {
        _state.update {
            it.copy(
                isSyncIntervalDialogVisible = true
            )
        }
    }

    private fun onLogOutClick() {
        viewModelScope.launch {
            if (!connectivityObserver.isConnected.first()) {
                _event.send(SettingsEvent.LogOutError(UiText.StringResource(R.string.you_need_an_internet_connection_to_log_out)))
                return@launch
            }

            val isHasPendingSync = notesRepository.isHasPendingSync()

            if (isHasPendingSync) {
                _state.update {
                    it.copy(
                        isUnsyncedDialogVisible = true
                    )
                }
            } else {
                performLogoutSync(false)
            }
        }
    }

    private fun performLogoutSync(isForceSync: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            when (val result = if (isForceSync) notesRepository.logoutAndSync() else notesRepository.logout()) {
                is Result.Error -> {
                    _event.send(SettingsEvent.LogOutError(result.error.asUiText()))
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }

                is Result.Success -> {
                    _event.send(SettingsEvent.LogOutSuccess)
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}