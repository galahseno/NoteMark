package com.icdid.dashboard.presentation.note_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.Result
import com.icdid.core.presentation.utils.asUiText
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.model.NoteDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter

class NoteDetailViewModel(
    private val noteId: String,
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(NoteDetailState())
    val state = _state.asStateFlow()

    private val _event = Channel<NoteDetailEvent>()
    val event = _event.receiveAsFlow()

    private val _originalTitle = MutableStateFlow("")
    private val _originalContent = MutableStateFlow("")
    private val _createdAt = MutableStateFlow("")

    private val hasChanges = combine(
        state,
        _originalTitle,
        _originalContent
    ) { currentState, originalTitle, originalContent ->
        currentState.title != originalTitle || currentState.content != originalContent
    }

    private val isEmpty = state.map { it.content.isEmpty() }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val note = notesRepository.getNote(noteId)

            note?.let {
                _originalTitle.value = note.title
                _originalContent.value = note.content
                _createdAt.value = note.createdAt

                _state.update {
                    it.copy(
                        title = note.title,
                        content = note.content
                    )
                }
            }

        }
    }

    fun onAction(action: NoteDetailAction) {
        when (action) {
            is NoteDetailAction.OnSaveNoteClicked -> {
                viewModelScope.launch {
                    val timeNow = DateTimeFormatter.ISO_INSTANT.format(Instant.now())

                    when (
                        val result = notesRepository.upsertNote(
                            note = NoteDomain(
                                id = noteId,
                                title = _state.value.title,
                                content = _state.value.content,
                                createdAt = _createdAt.value.ifEmpty { timeNow },
                                lastEditedAt = timeNow,
                            ),
                            isUpdate = state.value.title != _originalTitle.value || state.value.content != _originalContent.value
                        )) {
                        is Result.Error -> {
                            _event.send(NoteDetailEvent.Error(result.error.asUiText()))
                        }

                        is Result.Success -> {
                            _originalTitle.value = _state.value.title
                            _originalContent.value = _state.value.content
                            _event.send(NoteDetailEvent.NoteSaved)
                        }
                    }
                }
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
                viewModelScope.launch {
                    if (hasChanges.first()) {
                        _state.update { currentState ->
                            currentState.copy(isSaveNoteDialogVisible = true)
                        }
                    } else {
                        handleEmptyNoteCleanup()
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

            NoteDetailAction.OnConfirmationDialogConfirmed -> {
                handleEmptyNoteCleanup()
                viewModelScope.launch {
                    _event.send(NoteDetailEvent.OnDiscardChanges)
                }
            }
        }
    }

    private fun handleEmptyNoteCleanup() {
        viewModelScope.launch {
            if (isEmpty.first()) {
                notesRepository.deleteNote(noteId)
            }
        }
    }
}