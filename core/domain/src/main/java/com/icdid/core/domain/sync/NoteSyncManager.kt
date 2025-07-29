package com.icdid.core.domain.sync

import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult

interface NoteSyncManager {
    suspend fun syncPendingNotes(): EmptyResult<DataError>
}