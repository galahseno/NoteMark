package com.icdid.notemark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.SessionStorage
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    sessionStorage: SessionStorage
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        sessionStorage
            .get()
            .distinctUntilChanged()
            .onEach {
                state = state.copy(
                    isLoggedIn = it.username.isNotEmpty()
                )
            }
            .launchIn(viewModelScope)
    }
}