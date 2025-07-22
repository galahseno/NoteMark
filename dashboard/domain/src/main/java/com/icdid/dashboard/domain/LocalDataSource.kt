package com.icdid.dashboard.domain

import com.icdid.core.domain.DataError
import com.icdid.core.domain.Result
import com.icdid.dashboard.domain.model.NoteDomain
import com.icdid.dashboard.domain.model.SyncOperation
import kotlinx.coroutines.flow.Flow

typealias NoteId = String

interface LocalDataSource {
    fun getNotes(): Flow<List<NoteDomain>>
    suspend fun getNote(id: NoteId): NoteDomain?
    suspend fun upsertNote(note: NoteDomain): Result<NoteId, DataError.Local>
    suspend fun upsertNotes(notes: List<NoteDomain>): Result<List<NoteId>, DataError.Local>
    suspend fun deleteNote(id: NoteId)
    suspend fun deleteAllNotes()

    suspend fun insertPendingSync(
        noteId: NoteId? = null,
        noteDomain: NoteDomain? = null,
        operation: SyncOperation
    )
}