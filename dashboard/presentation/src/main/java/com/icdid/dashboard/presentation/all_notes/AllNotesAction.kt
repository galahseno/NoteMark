package com.icdid.dashboard.presentation.all_notes

sealed interface AllNotesAction {
    data object OnLongNoteDisplayDialog : AllNotesAction
}