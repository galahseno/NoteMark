package com.icdid.dashboard.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.Result
import com.icdid.core.domain.SessionStorage
import com.icdid.core.presentation.utils.asUiText
import com.icdid.dashboard.domain.NotesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val notesRepository: NotesRepository,
): ViewModel() {
    private val _event = Channel<SettingsEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(settingsAction: SettingsAction) {
        when(settingsAction) {
            is SettingsAction.OnLogOutClick -> onLogOutClick()
            else -> Unit
        }
    }

    private fun onLogOutClick() {
        viewModelScope.launch {
            when(val result = notesRepository.logout()) {
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