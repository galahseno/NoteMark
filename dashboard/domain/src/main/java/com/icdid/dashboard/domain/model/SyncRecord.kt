package com.icdid.dashboard.domain.model

data class SyncRecord(
    val id: String,
    val userId: String,
    val noteId: String?,
    val operation: String,
    val payload: NoteDomain?,
    val timestamp: Long
)
