package com.icdid.core.domain

import kotlinx.coroutines.flow.Flow

interface UserSettings {
    fun getSyncInterval(): Flow<String>
    suspend fun setSyncInterval(syncInterval: String)

    suspend fun getUserId(username: String): String
}