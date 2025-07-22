package com.icdid.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sync_record")
data class SyncRecordEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val noteId: String?,
    val operation: String,
    val payload: String?,
    val timestamp: Long
)
