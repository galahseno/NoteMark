package com.icdid.dashboard.domain

import kotlinx.coroutines.flow.Flow

interface UserSettings {
    fun getSyncInterval(): Flow<String>
    suspend fun setSyncInterval(syncInterval: String)
}