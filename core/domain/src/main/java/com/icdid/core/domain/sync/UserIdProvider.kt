package com.icdid.core.domain.sync

interface UserIdProvider {
    suspend fun getCurrentUserId(): String?
}