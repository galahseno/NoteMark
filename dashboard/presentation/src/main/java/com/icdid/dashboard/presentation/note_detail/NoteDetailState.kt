package com.icdid.dashboard.presentation.note_detail

data class NoteDetailState(
    val title: String = "",
    val content: String = "",
    val isSaveNoteDialogVisible: Boolean = false,
)