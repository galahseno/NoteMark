package com.icdid.core.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import com.icdid.core.data.database.SyncRecordDao
import com.icdid.core.domain.UserSettings
import com.icdid.core.domain.model.SyncInterval
import com.icdid.core.domain.sync.SyncNotesScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SyncNotesWorkerScheduler(
    private val context: Context,
    private val pendingDao: SyncRecordDao,
    private val userSettings: UserSettings,
    private val applicationScope: CoroutineScope
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

        // Worker contraint
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // TODO create periodic work request with interval, constraint and give initial delay
        // TODO for handle if sync failed we can use back off criteria
        // TODO enqueue work request with sync tag
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
    }
}