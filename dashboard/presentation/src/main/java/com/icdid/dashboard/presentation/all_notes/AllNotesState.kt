package com.icdid.dashboard.presentation.all_notes

import com.icdid.dashboard.domain.model.NoteDomain

data class AllNotesState(
    val username: String = "",
    val notes: List<NoteDomain> = emptyList(),
    val showDeleteDialog: Boolean = false,
    val isLoading: Boolean = true
)