package com.icdid.core.domain.sync

import com.icdid.core.domain.model.SyncInterval
import kotlinx.coroutines.flow.Flow

interface SyncNotesScheduler {
    suspend fun scheduleSync(interval: SyncInterval)
    suspend fun cancelSync()
    fun isSyncInProgress(): Flow<Boolean>
}