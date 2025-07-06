package com.icdid.dashboard.presentation.note_detail

import com.icdid.core.presentation.utils.UiText
import com.icdid.dashboard.presentation.note_detail.model.NoteDetailMode

data class NoteDetailState(
    val title: String = "",
    val content: String = "",
    val createdAt: UiText? = null,
    val lastEditedAt: UiText? = null,
    val noteMode: NoteDetailMode = NoteDetailMode.VIEW,
    val isSaveNoteDialogVisible: Boolean = false,
    val isNewNote: Boolean = false,
    val areUiElementsVisible: Boolean = true,
)