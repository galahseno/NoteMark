package com.icdid.notemark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.SessionStorage
import com.icdid.core.presentation.utils.NetworkMonitor
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    sessionStorage: SessionStorage,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        sessionStorage
            .get()
            .distinctUntilChangedBy { it.username }
            .map { it.username }
            .onEach {
                state = state.copy(
                    isLoggedIn = it.isNotEmpty()
                )
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        networkMonitor.stopMonitoring()
    }
}