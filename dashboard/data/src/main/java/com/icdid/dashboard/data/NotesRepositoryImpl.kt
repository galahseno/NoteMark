package com.icdid.dashboard.data

import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.Result
import com.icdid.core.domain.asEmptyDataResult
import com.icdid.dashboard.domain.model.NoteDomain
import com.icdid.dashboard.domain.model.SyncOperation
import com.icdid.core.domain.session.SessionStorage
import com.icdid.core.domain.session.UserSettings
import com.icdid.core.domain.sync.UserIdProvider
import com.icdid.dashboard.domain.LocalDataSource
import com.icdid.dashboard.domain.NoteId
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NotesRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage,
    private val userSettings: UserSettings,
    private val userIdProvider: UserIdProvider
) : NotesRepository {
    override fun getNotes(): Flow<List<NoteDomain>> {
        return localDataSource.getNotes()
    }

    override suspend fun getNote(id: NoteId): NoteDomain? {
        return localDataSource.getNote(id)
    }

    override suspend fun syncNotesAndFetchNotes(): EmptyResult<DataError> {
        val userId =
            userIdProvider.getCurrentUserId() ?: return Result.Error(DataError.Local.NO_DATA)
        val pendingSync = localDataSource.getPendingSync(userId)

        applicationScope.async {
            pendingSync.forEach { syncRecord ->
                when (val operation = SyncOperation.valueOf(syncRecord.operation)) {
                    SyncOperation.CREATE, SyncOperation.UPDATE -> {
                        val payload =
                            syncRecord.payload
                                ?: return@async Result.Error(DataError.Local.NO_DATA)
                        val result = remoteDataSource.upsertNote(
                            payload,
                            isUpdate = operation == SyncOperation.UPDATE
                        )
                        if (result is Result.Error) return@async Result.Error(result.error)

                        localDataSource.deletePendingSync(syncRecord.id)
                    }

                    SyncOperation.DELETE -> {
                        val noteId =
                            syncRecord.noteId
                                ?: return@async Result.Error(DataError.Local.NO_DATA)
                        val result = remoteDataSource.deleteNote(noteId)
                        if (result is Result.Error) return@async Result.Error(result.error)

                        localDataSource.deletePendingSync(syncRecord.id)
                    }
                }
            }
        }.await()

        val fetchNotes = fetchNotes()
        if (fetchNotes is Result.Error) return fetchNotes

        return Result.Success(Unit)
    }

    override suspend fun upsertNote(
        note: NoteDomain,
        isUpdate: Boolean
    ): Result<NoteId, DataError> {
        val result = localDataSource.upsertNote(note)

        if (result !is Result.Success) {
            return result
        }

        localDataSource.insertPendingSync(
            noteDomain = note,
            operation = if (isUpdate) SyncOperation.UPDATE else SyncOperation.CREATE
        )

        return result
    }


    override suspend fun deleteNote(id: NoteId) {
        with(localDataSource) {
            deleteNote(id)
            insertPendingSync(
                noteId = id,
                operation = SyncOperation.DELETE
            )
        }
    }

    override suspend fun logout(): EmptyResult<DataError> {
        val remoteResult = applicationScope.async {
            remoteDataSource.logout(sessionStorage.get().first().refreshToken)
        }.await()

        if (remoteResult is Result.Error) {
            return remoteResult.asEmptyDataResult()
        } else {
            applicationScope.async {
                localDataSource.deleteAllNotes()
                sessionStorage.clear()
                userSettings.clear()
            }.await()
        }
        return Result.Success(Unit)
    }

    override suspend fun logoutAndSync(): EmptyResult<DataError> {
        val result = syncPendingNotes()
        if (result is Result.Error) return result

        val logoutResult = logout()
        if (logoutResult is Result.Error) return logoutResult

        return Result.Success(Unit)
    }

    override suspend fun syncNotesManually(): EmptyResult<DataError> {
        val result = syncPendingNotes()
        if (result is Result.Error) return result

        userSettings.saveLastSyncTimestamp(System.currentTimeMillis())

        val fetchNotes = fetchNotes()
        if (fetchNotes is Result.Error) return fetchNotes

        return Result.Success(Unit)
    }

    override suspend fun isHasPendingSync(): Boolean {
        val pendingSync = localDataSource.getPendingSync(
            userIdProvider.getCurrentUserId() ?: return false
        )

        return pendingSync.isNotEmpty()
    }

    private suspend fun syncPendingNotes(): EmptyResult<DataError> {
        val userId =
            userIdProvider.getCurrentUserId() ?: return Result.Error(DataError.Local.NO_DATA)

        val pendingSync = localDataSource.getPendingSync(userId)
        if (pendingSync.isEmpty()) return Result.Error(DataError.Local.NO_DATA)

        pendingSync.forEach { syncRecord ->
            when (val operation = SyncOperation.valueOf(syncRecord.operation)) {
                SyncOperation.CREATE, SyncOperation.UPDATE -> {
                    val payload = syncRecord.payload ?: return Result.Error(DataError.Local.NO_DATA)
                    val result = remoteDataSource.upsertNote(
                        payload,
                        isUpdate = operation == SyncOperation.UPDATE
                    )
                    if (result is Result.Error) return Result.Error(result.error)

                    localDataSource.deletePendingSync(syncRecord.id)
                }

                SyncOperation.DELETE -> {
                    val noteId = syncRecord.noteId ?: return Result.Error(DataError.Local.NO_DATA)
                    val result = remoteDataSource.deleteNote(noteId)
                    if (result is Result.Error) return Result.Error(result.error)

                    localDataSource.deletePendingSync(syncRecord.id)
                }
            }
        }
        return Result.Success(Unit)
    }

    private suspend fun fetchNotes(): EmptyResult<DataError> {
        val result = remoteDataSource.getNotes()
        return when (result) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertNotes(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }
}