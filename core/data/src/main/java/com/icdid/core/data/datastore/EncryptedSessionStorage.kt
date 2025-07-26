package com.icdid.core.data.datastore

import androidx.datastore.core.DataStore
import com.icdid.core.data.model.AuthInfoSerializable
import com.icdid.core.data.model.toAuthInfo
import com.icdid.core.domain.session.SessionStorage
import com.icdid.core.domain.model.AuthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EncryptedSessionStorage(
    private val dataStore: DataStore<AuthInfoSerializable>,
): SessionStorage {

    override fun get(): Flow<AuthInfo> {
       return dataStore.data.map {
           it.toAuthInfo()
       }
    }

    override suspend fun set(info: AuthInfo) {
        dataStore.updateData {
            it.copy(
                accessToken = info.accessToken,
                refreshToken = info.refreshToken,
                username = info.username
            )
        }
    }

    override suspend fun clear() {
        dataStore.updateData {
            it.copy(
                accessToken = "",
                refreshToken = "",
                username = "",
            )
        }
    }
}