package com.icdid.core.data.sync

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import com.icdid.core.domain.model.SyncInterval
import com.icdid.core.domain.sync.SyncNotesScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class SyncNotesWorkerScheduler(
    private val context: Context,
): SyncNotesScheduler {
    override suspend fun scheduleSync(interval: SyncInterval) {
        // Cancel existing scheduled work
        cancelSync()

        // Determine repeat interval
        val intervalMinutes = when (interval) {
            SyncInterval.FifteenMinutes -> 15L
            SyncInterval.ThirtyMinutes -> 30L
            SyncInterval.OneHour -> 60L
            SyncInterval.Manual -> return // Don't schedule if Manual
        }

        // Worker constraint
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            repeatInterval = intervalMinutes,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                backoffDelay = SYNC_BACK_OFF_DELAY,
                timeUnit = TimeUnit.SECONDS,
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                SYNC_TAG,
                ExistingPeriodicWorkPolicy.UPDATE,
                syncRequest
            )
    }

    override suspend fun cancelSync() {
        WorkManager.getInstance(context)
            .cancelAllWork()
            .await()
    }

    override fun isSyncInProgress(): Flow<Boolean> {
        val workManager = WorkManager.getInstance(context)

        return workManager
            .getWorkInfosForUniqueWorkFlow(SYNC_TAG)
            .map { workInfoList ->
                workInfoList.any { it.state == WorkInfo.State.RUNNING }
            }
            .distinctUntilChanged()
    }

    companion object {
        private const val SYNC_TAG = "SYNC_WORKER"
        private const val SYNC_BACK_OFF_DELAY = 15L
    }
}