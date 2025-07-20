package com.icdid.dashboard.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.icdid.dashboard.domain.UserSettings
import com.icdid.dashboard.domain.model.SyncInterval
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserSettingsDataStore(
    private val dataStore: DataStore<Preferences>
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

    companion object {
        private val SYNC_INTERVAL_KEY = stringPreferencesKey("sync_interval")
    }

}