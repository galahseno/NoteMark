package com.icdid.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.icdid.core.data.conectivity.AndroidConnectivityObserver
import com.icdid.core.data.database.NoteMarkDatabase
import com.icdid.core.data.datastore.EncryptedSessionStorage
import com.icdid.core.data.model.AuthInfoSerializable
import com.icdid.core.data.network.HttpClientFactory
import com.icdid.core.data.security.AesEncryptionService
import com.icdid.core.data.security.KeyManager
import com.icdid.core.data.serializer.DataStoreSerializer
import com.icdid.core.data.sync.SyncNotesWorkerScheduler
import com.icdid.core.data.sync.UserIdProviderImpl
import com.icdid.core.domain.connectivity.ConnectivityObserver
import com.icdid.core.domain.encrypt.EncryptionService
import com.icdid.core.domain.session.SessionStorage
import com.icdid.core.domain.sync.SyncNotesScheduler
import com.icdid.core.domain.sync.UserIdProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private const val AUTH_PREFERENCES = "auth_preferences"
private const val DATABASE = "notemark_database"

val coreDataModule = module {
    single { HttpClientFactory(get()).build() }

    single<DataStore<AuthInfoSerializable>>(named(AUTH_PREFERENCES)) {
        DataStoreFactory.create(
            serializer = DataStoreSerializer(
                encryptionService = get(),
                serializer = AuthInfoSerializable.serializer(),
                defaultValueProvider = { AuthInfoSerializable() }
            ),
            produceFile = { androidContext().dataStoreFile(AUTH_PREFERENCES) }
        )
    }
    single {
        /**
         * for now we give the passphrase hardcoded, we can changed it to random and save it it encrypted files
         * maybe when we need to implemented encrypted room/sql soon
         **/
        KeyManager.getOrCreateSecretKey("NoteMarkPassphrase".toByteArray())
    }

    single {
        val migration1to2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS sync_record (
                id TEXT NOT NULL PRIMARY KEY,
                userId TEXT NOT NULL,
                noteId TEXT,
                operation TEXT NOT NULL,
                payload TEXT,
                timestamp INTEGER NOT NULL
            )
            """.trimIndent()
                )
            }
        }

        Room.databaseBuilder(
            get(),
            NoteMarkDatabase::class.java, DATABASE
        )
            .addMigrations(migration1to2)
            .build()
    }

    single { get<NoteMarkDatabase>().noteDao() }
    single { get<NoteMarkDatabase>().syncRecordDao() }

    single {
        EncryptedSessionStorage(
            dataStore = get(named(AUTH_PREFERENCES)),
        )
    } bind SessionStorage::class
    singleOf(::AesEncryptionService) bind EncryptionService::class
    singleOf(::SyncNotesWorkerScheduler) bind SyncNotesScheduler::class
    singleOf(::UserIdProviderImpl) bind UserIdProvider::class
    singleOf(::AndroidConnectivityObserver) bind ConnectivityObserver::class
}