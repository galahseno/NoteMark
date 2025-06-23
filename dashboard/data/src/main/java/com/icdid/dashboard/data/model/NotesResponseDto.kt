package com.icdid.dashboard.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NotesResponseDto(
    val notes: List<NoteDto>,
    val total: Int
)