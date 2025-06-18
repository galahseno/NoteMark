package com.icdid.dashboard.presentation.all_notes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

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