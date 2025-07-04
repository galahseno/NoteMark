package com.icdid.dashboard.data.local

import android.database.sqlite.SQLiteFullException
import com.icdid.core.data.database.NoteDao
import com.icdid.core.data.model.NoteEntity
import com.icdid.core.domain.DataError
import com.icdid.core.domain.Result
import com.icdid.dashboard.domain.LocalDataSource
import com.icdid.dashboard.domain.NoteId
import com.icdid.dashboard.domain.model.NoteDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource(
    private val noteDao: NoteDao,
): LocalDataSource {
    override fun getNotes(): Flow<List<NoteDomain>> {
        return noteDao.getNotes().map { it.map { noteEntity ->
                NoteDomain(
                    id = noteEntity.id,
                    title = noteEntity.title,
                    content = noteEntity.content,
                    createdAt = noteEntity.createdAt,
                    lastEditedAt = noteEntity.lastEditedAt,
                )
            }
        }
    }

    override fun getNote(id: NoteId): NoteDomain? {
        return noteDao.getNote(id)?.run {
            NoteDomain(
                id = id,
                title = title,
                content = content,
                createdAt = createdAt,
                lastEditedAt = lastEditedAt,
            )
        }
    }

    override suspend fun upsertNote(note: NoteDomain): Result<NoteId, DataError.Local> {
        return try {
            val noteEntity = note.run {
                NoteEntity(
                    id = id,
                    title = title,
                    content = content,
                    createdAt = createdAt,
                    lastEditedAt = lastEditedAt,
                )
            }
            noteDao.upsertNote(noteEntity)
            Result.Success(note.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertNotes(notes: List<NoteDomain>): Result<List<NoteId>, DataError.Local> {
        return try {
            notes.forEach { noteDomain ->
                val noteEntity = noteDomain.run {
                    NoteEntity(
                        id = id,
                        title = title,
                        content = content,
                        createdAt = createdAt,
                        lastEditedAt = lastEditedAt,
                    )
                }
                noteDao.upsertNote(noteEntity)
            }
            Result.Success(emptyList())
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteNote(id: NoteId) {
        noteDao.delete(id)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAll()
    }
}