package com.icdid.dashboard.presentation.all_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class AllNotesViewModel : ViewModel() {
    private val _state = MutableStateFlow(AllNotesState())
    val state = _state

    private val _event = Channel<AllNotesEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: AllNotesAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }
}