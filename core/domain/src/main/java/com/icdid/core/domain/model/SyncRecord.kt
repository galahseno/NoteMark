package com.icdid.core.domain.model

data class SyncRecord(
    val id: String,
    val userId: String,
    val noteId: String?,
    val operation: String,
    val payload: String?,
    val timestamp: Long
)
