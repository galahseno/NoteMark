package com.icdid.dashboard.presentation.note_detail

sealed interface NoteDetailEvent {
    data object OnDiscardChanges: NoteDetailEvent
}