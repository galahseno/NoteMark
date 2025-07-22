package com.icdid.core.data.sync

import com.icdid.core.domain.SessionStorage
import com.icdid.core.domain.UserSettings
import com.icdid.core.domain.sync.UserIdProvider
import kotlinx.coroutines.flow.first

class UserIdProviderImpl(
    private val sessionStorage: SessionStorage,
    private val userSettings: UserSettings
) : UserIdProvider {

    override suspend fun getCurrentUserId(): String? {
        val username = sessionStorage.get().first().username
        return if (username.isNotEmpty()) {
            userSettings.getUserId(username)
        } else null
    }
}