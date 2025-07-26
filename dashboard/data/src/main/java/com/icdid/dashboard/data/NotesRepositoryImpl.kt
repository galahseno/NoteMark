package com.icdid.dashboard.data

import android.util.Log
import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.Result
import com.icdid.core.domain.asEmptyDataResult
import com.icdid.core.domain.model.SyncRecord
import com.icdid.core.domain.session.SessionStorage
import com.icdid.core.domain.session.UserSettings
import com.icdid.dashboard.data.model.NoteDto
import com.icdid.dashboard.data.model.toNoteDomain
import com.icdid.dashboard.domain.LocalDataSource
import com.icdid.dashboard.domain.NoteId
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.RemoteDataSource
import com.icdid.dashboard.domain.model.NoteDomain
import com.icdid.dashboard.domain.model.SyncOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class NotesRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage,
    private val userSettings: UserSettings
) : NotesRepository {
    override fun getNotes(): Flow<List<NoteDomain>> {
        return localDataSource.getNotes()
    }

    override suspend fun getNote(id: NoteId): NoteDomain? {
        return localDataSource.getNote(id)
    }

    override suspend fun fetchNotes(): EmptyResult<DataError> {
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

    override suspend fun logout(): EmptyResult<DataError.Network> {
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

    override suspend fun syncNotesManually(): EmptyResult<DataError> {
        val pendingSync = localDataSource.getPendingSync()

        if (pendingSync.isEmpty()) return Result.Error(DataError.Local.NO_DATA)

        pendingSync.forEach { syncRecord ->
            when (val operation = SyncOperation.valueOf(syncRecord.operation)) {
                SyncOperation.CREATE, SyncOperation.UPDATE -> {
                    when (val parsedNoteResult = parseNote(syncRecord)) {
                        is Result.Error -> return parsedNoteResult.asEmptyDataResult()
                        is Result.Success -> {
                            val result = remoteDataSource.upsertNote(
                                parsedNoteResult.data,
                                isUpdate = operation == SyncOperation.UPDATE
                            )
                            if (result is Result.Error) return Result.Error(result.error)

                            localDataSource.deletePendingSync(syncRecord.id)
                        }
                    }
                }

                SyncOperation.DELETE -> {
                    val noteId = syncRecord.noteId ?: return Result.Error(DataError.Local.NO_DATA)
                    val result = remoteDataSource.deleteNote(noteId)
                    if (result is Result.Error) return Result.Error(result.error)

                    localDataSource.deletePendingSync(syncRecord.id)
                }
            }
        }

        userSettings.saveLastSyncTimestamp(System.currentTimeMillis())
        fetchNotes()
        return Result.Success(Unit)
    }

    private fun parseNote(syncRecord: SyncRecord): Result<NoteDomain, DataError> {
        return try {
            val note = syncRecord.payload
                ?.let { Json.decodeFromString<NoteDto>(it) }
                ?.toNoteDomain()
                ?: return Result.Error(DataError.Local.NO_DATA)

            Result.Success(note)
        } catch (e: Exception) {
            Log.e("Sync", "Failed to parse note: ${syncRecord.payload}", e)
            Result.Error(DataError.Network.SERIALIZATION)
        }
    }
}