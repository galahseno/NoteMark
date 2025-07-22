package com.icdid.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.icdid.core.domain.EncryptionService
import com.icdid.core.domain.UserSettings
import com.icdid.core.domain.model.SyncInterval
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

class UserSettingsDataStore(
    private val dataStore: DataStore<Preferences>,
    private val encryptionService: EncryptionService
) : UserSettings {
    override fun getSyncInterval(): Flow<String> {
        return dataStore.data.map {
            it[SYNC_INTERVAL_KEY] ?: SyncInterval.Manual.name
        }
    }

    override suspend fun setSyncInterval(syncInterval: String) {
        dataStore.edit {
            it[SYNC_INTERVAL_KEY] = syncInterval
        }
    }

    override suspend fun getUserId(username: String): String {
        val hashedKey = encryptionService.hashKey(username)
        val key = stringPreferencesKey(hashedKey)

        val prefs = dataStore.data.first()
        return prefs[key] ?: UUID.randomUUID().also { newId ->
            dataStore.edit { it[key] = newId.toString() }
        }.toString()
    }

    companion object {
        private val SYNC_INTERVAL_KEY = stringPreferencesKey("sync_interval")
    }
}