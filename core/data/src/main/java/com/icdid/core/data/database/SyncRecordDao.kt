package com.icdid.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.icdid.core.data.model.SyncRecordEntity

@Dao
interface SyncRecordDao {
    @Query("SELECT * FROM sync_record order by timestamp asc")
    suspend fun getPendingSync(): List<SyncRecordEntity>

    @Insert
    suspend fun insertPendingSync(syncRecordEntity: SyncRecordEntity)

    @Query("DELETE FROM sync_record WHERE id = :id")
    suspend fun delete(id: String)
}