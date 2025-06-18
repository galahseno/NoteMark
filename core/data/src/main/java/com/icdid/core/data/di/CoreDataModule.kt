package com.icdid.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.icdid.core.data.model.AuthInfoSerializable
import com.icdid.core.data.network.HttpClientFactory
import com.icdid.core.data.security.AesEncryptionService
import com.icdid.core.data.security.KeyManager
import com.icdid.core.data.serializer.DataStoreSerializer
import com.icdid.core.data.session.EncryptedSessionStorage
import com.icdid.core.domain.EncryptionService
import com.icdid.core.domain.SessionStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val AUTH_PREFERENCES = "auth_preferences"

val coreDataModule = module {
    single { HttpClientFactory(get()).build() }

    single<DataStore<AuthInfoSerializable>> {
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

    singleOf(::EncryptedSessionStorage) bind SessionStorage::class
    singleOf(::AesEncryptionService) bind EncryptionService::class
}