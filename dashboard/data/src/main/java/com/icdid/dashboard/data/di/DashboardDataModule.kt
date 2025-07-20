package com.icdid.dashboard.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.icdid.dashboard.data.NotesRepositoryImpl
import com.icdid.dashboard.data.UserSettingsDataStore
import com.icdid.dashboard.data.local.RoomLocalDataSource
import com.icdid.dashboard.data.network.KtorRemoteDataSource
import com.icdid.dashboard.domain.LocalDataSource
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.RemoteDataSource
import com.icdid.dashboard.domain.UserSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


private const val PREFERENCES_DATA_STORE_NAME = "user_settings"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_DATA_STORE_NAME
)

val dashboardDataModule = module {
    singleOf(::KtorRemoteDataSource) bind RemoteDataSource::class
    singleOf(::RoomLocalDataSource) bind LocalDataSource::class
    singleOf(::NotesRepositoryImpl) bind NotesRepository::class

    single<DataStore<Preferences>>(named(PREFERENCES_DATA_STORE_NAME)) {
        androidContext().dataStore
    }

    single {
        UserSettingsDataStore(
            get(named(PREFERENCES_DATA_STORE_NAME))
        )
    } bind UserSettings::class
}