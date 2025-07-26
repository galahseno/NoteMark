package com.icdid.notemark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.model.SyncInterval
import com.icdid.core.domain.session.SessionStorage
import com.icdid.core.domain.session.UserSettings
import com.icdid.core.domain.sync.SyncNotesScheduler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    sessionStorage: SessionStorage,
    userSettings: UserSettings,
    syncNotesScheduler: SyncNotesScheduler
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    private val usernameFlow = sessionStorage
        .get()
        .map { it.username }
        .distinctUntilChanged()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )

    init {
        usernameFlow
            .onEach { username ->
                state = state.copy(isLoggedIn = username?.isNotEmpty())
            }
            .launchIn(viewModelScope)

        userSettings
            .getSyncInterval()
            .distinctUntilChanged()
            .onEach { interval ->
                val username = usernameFlow.value
                val interval = SyncInterval.valueOf(interval)
                if (username?.isNotEmpty() == true && interval != SyncInterval.Manual) {
                    syncNotesScheduler.scheduleSync(interval)
                } else {
                    syncNotesScheduler.cancelSync()
                }
            }
            .launchIn(viewModelScope)
    }
}