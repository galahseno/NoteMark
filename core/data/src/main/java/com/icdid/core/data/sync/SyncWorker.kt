package com.icdid.core.data.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.icdid.core.domain.DataError
import com.icdid.core.domain.Result.Error
import com.icdid.core.domain.Result.Success
import com.icdid.core.domain.sync.NoteSyncManager
import timber.log.Timber

class SyncWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val noteSyncManager: NoteSyncManager
): CoroutineWorker(context, workerParameters) {

    private val MAX_RETRIES = 5

    override suspend fun doWork(): Result {
        val attempt = runAttemptCount
        return when(val syncResult = noteSyncManager.syncPendingNotes()) {
            is Error -> {
                val syncError = when(val error = syncResult.error) {
                    is DataError.Network -> error.name
                    is DataError.Local -> error.name
                }
                if(attempt >= MAX_RETRIES) {
                    Result.failure(workDataOf(
                        "error" to "Max retries reached. Last sync error: $syncError"
                    ))
                } else {
                    Timber.e("Sync error with exception: $syncError. Trying again. Current run attempt: ")
                    Result.retry()
                }
            }
            is Success -> Result.success()
        }
    }
}