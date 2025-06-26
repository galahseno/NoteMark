package com.icdid.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.icdid.core.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteMarkDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}