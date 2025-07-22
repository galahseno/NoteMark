package com.icdid.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.icdid.core.data.model.NoteEntity
import com.icdid.core.data.model.SyncRecordEntity

@Database(
    entities = [
        NoteEntity::class,
        SyncRecordEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class NoteMarkDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun syncRecordDao(): SyncRecordDao
}