package com.icdid.dashboard.presentation.note_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteDetailViewModel : ViewModel() {
    private val _state = MutableStateFlow(NoteDetailState())
    val state = _state.asStateFlow()

    private val _event = Channel<NoteDetailEvent>()
    val event = _event.receiveAsFlow()


    fun onAction(action: NoteDetailAction) {
        when (action) {
            is NoteDetailAction.OnSaveNoteClicked -> {
                // Handle save note action
            }
            is NoteDetailAction.OnNoteTitleChanged -> {
                _state.update { currentState ->
                    currentState.copy(
                        title = action.title,
                    )
                }
            }
            is NoteDetailAction.OnNoteContentChanged -> {
                _state.update { currentState ->
                    currentState.copy(
                        content = action.content,
                    )
                }
            }
            is NoteDetailAction.OnCloseClicked -> {
                if(state.value.title.isNotEmpty() && state.value.content.isNotEmpty()) {
                    _state.update { currentState ->
                        currentState.copy(
                            isSaveNoteDialogVisible = true,
                        )
                    }
                } else {
                    viewModelScope.launch {
                        _event.send(NoteDetailEvent.OnDiscardChanges)
                    }
                }
            }
            is NoteDetailAction.OnConfirmationDialogDismissed -> {
                _state.update { currentState ->
                    currentState.copy(
                        isSaveNoteDialogVisible = false,
                    )
                }
            }
            else -> Unit
        }
    }
}