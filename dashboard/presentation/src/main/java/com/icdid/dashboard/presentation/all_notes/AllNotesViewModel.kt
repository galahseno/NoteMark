package com.icdid.dashboard.presentation.all_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.Result
import com.icdid.core.domain.connectivity.ConnectivityObserver
import com.icdid.core.domain.session.SessionStorage
import com.icdid.core.presentation.utils.asUiText
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.model.NoteDomain
import com.icdid.dashboard.presentation.util.toInitials
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID

class AllNotesViewModel(
    sessionStorage: SessionStorage,
    private val notesRepository: NotesRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(AllNotesState())
    val state = _state.asStateFlow()

    private val _event = Channel<AllNotesEvent>()
    val event = _event.receiveAsFlow()

    private var selectedNoteId = ""

    init {
        observeNetworkMonitor()
        observeUserSession(sessionStorage)
        loadNotes()
        observeNotes()
    }

    fun onAction(action: AllNotesAction) {
        when (action) {
            is AllNotesAction.OnLongNoteDisplayDialog -> showDeleteDialog(action.id)
            is AllNotesAction.OnCreateNote -> createNote()
            is AllNotesAction.OnDeleteNoteConfirmed -> deleteNote()
            else -> Unit
        }
    }

    private fun observeNetworkMonitor() {
        connectivityObserver.isConnected
            .onEach { isConnected ->
                _state.update {
                    it.copy(
                        isNetworkAvailable = isConnected
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeUserSession(sessionStorage: SessionStorage) {
        sessionStorage
            .get()
            .map { it.username }
            .distinctUntilChanged()
            .onEach { username ->
                _state.update {
                    it.copy(username = username.toInitials())
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(500)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun observeNotes() {
        notesRepository
            .getNotes()
            .onEach { notes ->
                _state.update { currentState ->
                    currentState.copy(
                        notes = notes,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun showDeleteDialog(noteId: String) {
        selectedNoteId = noteId
        _state.update {
            it.copy(showDeleteDialog = !it.showDeleteDialog)
        }
    }

    private fun createNote() {
        viewModelScope.launch {
            val timeNow = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            val newNote = NoteDomain(
                id = UUID.randomUUID().toString(),
                createdAt = timeNow,
                lastEditedAt = timeNow
            )

            when (val result = notesRepository.upsertNote(note = newNote, isUpdate = false)) {
                is Result.Error -> {
                    _event.send(AllNotesEvent.Error(result.error.asUiText()))
                }
                is Result.Success -> {
                    _event.send(AllNotesEvent.NoteSaved(result.data))
                }
            }
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            if (selectedNoteId.isNotEmpty()) {
                notesRepository.deleteNote(selectedNoteId)
                selectedNoteId = ""
            }
            hideDeleteDialog()
        }
    }

    private fun hideDeleteDialog() {
        _state.update {
            it.copy(showDeleteDialog = false)
        }
    }
}