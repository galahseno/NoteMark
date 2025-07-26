package com.icdid.core.domain.session

import kotlinx.coroutines.flow.Flow

interface UserSettings {
    fun getSyncInterval(): Flow<String>
    suspend fun setSyncInterval(syncInterval: String)

    suspend fun getUserId(username: String): String

    suspend fun saveLastSyncTimestamp(timestamp: Long)
    fun getLastSyncTimestamp(): Flow<Long?>

    suspend fun clear()
}