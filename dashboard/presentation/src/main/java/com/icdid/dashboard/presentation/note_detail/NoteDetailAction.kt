package com.icdid.dashboard.presentation.note_detail

import com.icdid.dashboard.presentation.note_detail.model.NoteDetailMode

sealed interface NoteDetailAction {
    data object OnCloseClicked: NoteDetailAction
    data object OnSaveNoteClicked: NoteDetailAction
    data class OnNoteTitleChanged(val title: String): NoteDetailAction
    data class OnNoteContentChanged(val content: String): NoteDetailAction
    data class OnChangeMode(val detailMode: NoteDetailMode): NoteDetailAction
    data object OnReadModeTap: NoteDetailAction
    data object OnScrollStarted: NoteDetailAction
}