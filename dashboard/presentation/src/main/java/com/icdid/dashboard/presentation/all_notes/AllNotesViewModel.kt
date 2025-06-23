package com.icdid.dashboard.presentation.all_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.core.domain.Result
import com.icdid.core.domain.SessionStorage
import com.icdid.core.presentation.utils.asUiText
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.model.NoteDomain
import com.icdid.dashboard.presentation.util.toInitials
import kotlinx.coroutines.channels.Channel
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
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AllNotesState())
    val state = _state.asStateFlow()

    private val _event = Channel<AllNotesEvent>()
    val event = _event.receiveAsFlow()

    private var noteId = MutableStateFlow("")

    init {
        sessionStorage
            .get()
            .map { it.username }
            .distinctUntilChanged()
            .onEach { username ->
                _state.update {
                    it.copy(
                        username = username.toInitials()
                    )
                }
            }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            notesRepository.fetchNotes()
        }

        notesRepository
            .getNotes()
            .onEach {
                _state.update {
                    it.copy(
                        notes = it.notes,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: AllNotesAction) {
        when (action) {
            is AllNotesAction.OnLongNoteDisplayDialog -> {
                noteId.value = action.id
                _state.update {
                    it.copy(
                        showDeleteDialog = !it.showDeleteDialog
                    )
                }
            }

            AllNotesAction.OnCreateNote -> {
                viewModelScope.launch {
                    val timeNow = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                    when (
                        val result = notesRepository.upsertNote(
                            note = NoteDomain(
                                id = UUID.randomUUID().toString(),
                                createdAt = timeNow,
                                lastEditedAt = timeNow
                            ),
                            isUpdate = false
                        )) {
                        is Result.Error -> {
                            _event.send(AllNotesEvent.Error(result.error.asUiText()))
                        }

                        is Result.Success -> {
                            _event.send(AllNotesEvent.NoteSaved(result.data))
                        }
                    }
                }
            }

            AllNotesAction.OnDeleteNoteConfirmed -> {
                viewModelScope.launch {
                    if (noteId.value.isNotEmpty()) {
                        notesRepository.deleteNote(noteId.value)
                    }
                    _state.update {
                        it.copy(
                            showDeleteDialog = !it.showDeleteDialog
                        )
                    }
                }
            }

            else -> Unit
        }
    }
}