package com.icdid.dashboard.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.Result
import com.icdid.core.presentation.utils.asUiText
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.UserSettings
import com.icdid.dashboard.domain.model.SyncInterval
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val notesRepository: NotesRepository,
    private val userSettings: UserSettings
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    private val _event = Channel<SettingsEvent>()
    val event = _event.receiveAsFlow()

    init {
        userSettings
            .getSyncInterval()
            .distinctUntilChanged()
            .onEach { interval ->
                _state.update {
                    it.copy(
                        syncInterval = SyncInterval.valueOf(interval)
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnLogOutClick -> onLogOutClick()
            SettingsAction.OnSyncDataClick -> Unit
            SettingsAction.OnSyncIntervalClick -> onSyncIntervalClick()
            SettingsAction.OnSyncIntervalDialogDismiss -> onSyncIntervalDialogDismiss()
            is SettingsAction.OnSyncIntervalDialogClick -> onSyncIntervalDialogClick(action.syncInterval)
            else -> Unit
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
            when (val result = notesRepository.logout()) {
                is Result.Error -> {
                    _event.send(SettingsEvent.LogOutError(result.error.asUiText()))
                }

                is Result.Success -> {
                    _event.send(SettingsEvent.LogOutSuccess)
                }
            }
        }
    }
}