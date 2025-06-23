package com.icdid.dashboard.presentation.note_detail

import com.icdid.core.presentation.utils.UiText

sealed interface NoteDetailEvent {
    data object OnDiscardChanges: NoteDetailEvent
    data class Error(val error: UiText): NoteDetailEvent
    data object NoteSaved: NoteDetailEvent
}