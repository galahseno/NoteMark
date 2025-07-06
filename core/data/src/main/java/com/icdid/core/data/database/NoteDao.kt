package com.icdid.core.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.icdid.core.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes order by createdAt desc")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: String): NoteEntity?

    @Upsert
    suspend fun upsertNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}