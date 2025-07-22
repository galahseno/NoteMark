@file:OptIn(FlowPreview::class)

package com.icdid.dashboard.presentation.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.Result
import com.icdid.core.presentation.utils.asUiText
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.model.NoteDomain
import com.icdid.dashboard.presentation.note_detail.NoteDetailEvent.Error
import com.icdid.dashboard.presentation.note_detail.model.NoteDetailMode
import com.icdid.dashboard.presentation.util.formatDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter

class NoteDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val noteId = savedStateHandle.get<String>(NOTE_ID_KEY) ?: ""
    private val isNewNote = savedStateHandle.get<Boolean>(NEW_NOTE_KEY) ?: false

    private val _state = MutableStateFlow(NoteDetailState())
    val state = _state.asStateFlow()

    private val _event = Channel<NoteDetailEvent>()
    val event = _event.receiveAsFlow()

    private var _originalNote: NoteDomain? = null
    private var countdownJob: Job? = null

    init {
        loadNote()
        viewModelScope.launch {
            _state
                .map { it.title to it.content }
                .distinctUntilChanged()
                .debounce(NOTE_SAVING_TIME)
                .collect {
                    if (hasUnsavedChanges()) {
                        saveNote()
                    }
                }
        }
    }

    private fun loadNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = notesRepository.getNote(noteId)
            _originalNote = note

            note?.let {
                _state.update { currentState ->
                    currentState.copy(
                        title = savedStateHandle[NOTE_TITLE_KEY] ?: note.title,
                        content = savedStateHandle[NOTE_CONTENT_KEY] ?: note.content,
                        createdAt = note.createdAt.formatDate(),
                        lastEditedAt = note.lastEditedAt.formatDate(),
                        noteMode = NoteDetailMode.valueOf(
                            savedStateHandle[NOTE_MODE_KEY]
                                ?: if (isNewNote) NoteDetailMode.EDIT.name else NoteDetailMode.VIEW.name
                        ),
                        isNewNote = isNewNote
                    )
                }
            }
        }
    }

    fun onAction(action: NoteDetailAction) {
        when (action) {
            is NoteDetailAction.OnSaveNoteClicked -> saveNote()
            is NoteDetailAction.OnNoteTitleChanged -> updateTitle(action.title)
            is NoteDetailAction.OnNoteContentChanged -> updateContent(action.content)
            is NoteDetailAction.OnCloseClicked -> handleClose()
            is NoteDetailAction.OnChangeMode -> changeMode(action.detailMode)
            NoteDetailAction.OnReadModeTap -> onReadModeTap()
            NoteDetailAction.OnScrollStarted -> onScrollStarted()
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            val currentState = _state.value
            val timeNow = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            val hasChanges = hasUnsavedChanges()

            val result = notesRepository.upsertNote(
                note = NoteDomain(
                    id = noteId,
                    title = currentState.title,
                    content = currentState.content,
                    createdAt = _originalNote?.createdAt ?: timeNow,
                    lastEditedAt = timeNow,
                ),
                isUpdate = hasChanges
            )

            when (result) {
                is Result.Error -> {
                    _event.send(Error(result.error.asUiText()))
                }

                is Result.Success -> {
                    _originalNote = _originalNote?.copy(
                        title = currentState.title,
                        content = currentState.content,
                        lastEditedAt = timeNow
                    )
                    _state.update {
                        it.copy(isNewNote = false)
                    }
                    savedStateHandle[NEW_NOTE_KEY] = false
                }
            }
        }
    }

    private fun updateTitle(title: String) {
        savedStateHandle[NOTE_TITLE_KEY] = title
        _state.update { it.copy(title = title) }
    }

    private fun updateContent(content: String) {
        savedStateHandle[NOTE_CONTENT_KEY] = content
        _state.update { it.copy(content = content) }
    }

    private fun handleClose() {
        viewModelScope.launch {
            when (_state.value.noteMode) {
                NoteDetailMode.VIEW, NoteDetailMode.READ -> {
                    _event.send(NoteDetailEvent.OnDiscardChanges)
                }

                NoteDetailMode.EDIT -> {
                    if (_state.value.isNewNote && !hasUnsavedChanges()) {
                        deleteNewNote()
                        _event.send(NoteDetailEvent.OnDiscardChanges)
                    } else {
                        _state.update {
                            it.copy(
                                noteMode = NoteDetailMode.VIEW,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun changeMode(mode: NoteDetailMode) {
        savedStateHandle[NOTE_MODE_KEY] = mode.name
        if (mode == NoteDetailMode.READ) {
            startCountdown()
        } else {
            stopCountdown()
        }
        _state.update {
            it.copy(
                noteMode = mode,
            )
        }
    }

    private fun onReadModeTap() {
        if (!_state.value.areUiElementsVisible) {
            startCountdown()
        } else {
            stopCountdown()
        }
        _state.update { currentState ->
            currentState.copy(
                areUiElementsVisible = !currentState.areUiElementsVisible
            )
        }
    }

    private fun onScrollStarted() {
        _state.update { currentState ->
            currentState.copy(
                areUiElementsVisible = false,
            )
        }
    }

    private fun deleteNewNote() {
        viewModelScope.launch {
            notesRepository.deleteNote(noteId)
        }
    }

    private fun hasUnsavedChanges(): Boolean {
        val currentState = _state.value
        return _originalNote?.let { original ->
            currentState.title != original.title || currentState.content != original.content
        } ?: false
    }

    private fun startCountdown() {
        countdownJob?.cancel()

        countdownJob = viewModelScope.launch(Dispatchers.IO) {
            for (i in 5 downTo 0) {
                delay(1000)
            }
            _state.update { currentState ->
                currentState.copy(
                    areUiElementsVisible = false
                )
            }
        }
    }

    private fun stopCountdown() {
        countdownJob?.cancel()
        countdownJob = null
    }

    companion object {
        private const val NOTE_ID_KEY = "noteId"
        private const val NEW_NOTE_KEY = "isNewNote"
        private const val NOTE_TITLE_KEY = "noteTitle"
        private const val NOTE_CONTENT_KEY = "noteContent"
        private const val NOTE_MODE_KEY = "noteMode"
        private const val NOTE_SAVING_TIME = 500L
    }
}