package com.icdid.core.domain.session

import com.icdid.core.domain.model.AuthInfo
import kotlinx.coroutines.flow.Flow

interface SessionStorage {
    fun get(): Flow<AuthInfo>
    suspend fun set(info: AuthInfo)
    suspend fun clear()
}