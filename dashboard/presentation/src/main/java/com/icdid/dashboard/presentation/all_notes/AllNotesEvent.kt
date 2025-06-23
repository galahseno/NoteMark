package com.icdid.dashboard.presentation.all_notes

import com.icdid.core.presentation.utils.UiText
import com.icdid.dashboard.domain.NoteId

sealed interface AllNotesEvent {
    data class Error(val error: UiText): AllNotesEvent
    data class NoteSaved(val id: NoteId): AllNotesEvent
}