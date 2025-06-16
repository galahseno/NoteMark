package com.icdid.dashboard.presentation.all_notes

import kotlinx.serialization.Serializable

data class AllNotesState(
    val username: String = "",
    val notes: List<NoteSample> = emptyList(),
    val showDeleteDialog: Boolean = false,
)

// TODO change to list note domain or ui model soon
@Serializable
data class NoteSample(
    val date: String,
    val title: String,
    val content: String,
)