package com.icdid.dashboard.presentation.note_detail

sealed interface NoteDetailAction {
    data object OnCloseClicked: NoteDetailAction
    data object OnSaveNoteClicked: NoteDetailAction
    data class OnNoteTitleChanged(val title: String): NoteDetailAction
    data class OnNoteContentChanged(val content: String): NoteDetailAction
    data object OnConfirmationDialogDismissed: NoteDetailAction
    data object OnConfirmationDialogConfirmed: NoteDetailAction
}