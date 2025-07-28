package com.icdid.dashboard.data

import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.Result
import com.icdid.core.domain.session.UserSettings
import com.icdid.core.domain.sync.NoteSyncManager
import com.icdid.core.domain.sync.SyncOperation
import com.icdid.core.domain.sync.UserIdProvider
import com.icdid.dashboard.domain.LocalDataSource
import com.icdid.dashboard.domain.RemoteDataSource

class NoteSyncManagerImpl (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val userIdProvider: UserIdProvider,
    private val userSettings: UserSettings,
): NoteSyncManager {
    override suspend fun syncPendingNotes(): EmptyResult<DataError> {
        val userId =
            userIdProvider.getCurrentUserId() ?: return Result.Error(DataError.Local.NO_DATA)

        val pendingSync = localDataSource.getPendingSync(userId)
        if (pendingSync.isEmpty()) return Result.Error(DataError.Local.NO_DATA)

        pendingSync.forEach { syncRecord ->
            when (val operation = SyncOperation.valueOf(syncRecord.operation)) {
                SyncOperation.CREATE, SyncOperation.UPDATE -> {
                    val payload = syncRecord.payload ?: return Result.Error(DataError.Local.NO_DATA)
                    val result = remoteDataSource.upsertNote(
                        note = payload,
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

        userSettings.saveLastSyncTimestamp(System.currentTimeMillis())
        return Result.Success(Unit)
    }
}