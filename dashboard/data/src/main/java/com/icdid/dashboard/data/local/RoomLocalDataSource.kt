package com.icdid.dashboard.data.local

import android.database.sqlite.SQLiteFullException
import com.icdid.core.domain.DataError
import com.icdid.core.domain.Result
import com.icdid.dashboard.domain.LocalDataSource
import com.icdid.dashboard.domain.NoteId
import com.icdid.dashboard.domain.model.NoteDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RoomLocalDataSource(
    // TODO Inject dao
): LocalDataSource {
    override fun getNotes(): Flow<List<NoteDomain>> {
        // TODO handle get notes
        return flowOf(emptyList())
    }

    override fun getNote(id: NoteId): NoteDomain {
        // TODO handle get note
        return NoteDomain()
    }

    override fun upsertNote(note: NoteDomain): Result<NoteId, DataError.Local> {
        return try {
            // TODO insert note to db
            Result.Success(note.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertNotes(notes: List<NoteDomain>): Result<List<NoteId>, DataError.Local> {
        return try {
            // TODO insert notes to db
            Result.Success(emptyList())
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteNote(id: NoteId) {
        // TODO delete note
    }

    override suspend fun deleteAllNotes() {
        // TODO delete all notes
    }
}