package com.icdid.dashboard.data.model

import com.icdid.dashboard.domain.model.NoteDomain
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditedAt: String
)

fun NoteDomain.toNoteRequest() : NoteDto {
    return NoteDto(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        lastEditedAt = lastEditedAt
    )
}

fun NoteDto.toNoteDomain() : NoteDomain {
    return NoteDomain(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        lastEditedAt = lastEditedAt
    )
}
