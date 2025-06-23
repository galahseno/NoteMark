package com.icdid.dashboard.presentation.all_notes

import com.icdid.dashboard.domain.NoteId

sealed interface AllNotesAction {
    data object OnCreateNote : AllNotesAction
    data class OnNoteClick(val id: NoteId) : AllNotesAction
    data class OnLongNoteDisplayDialog(val id: NoteId) : AllNotesAction
    data object OnDeleteNoteConfirmed : AllNotesAction
}