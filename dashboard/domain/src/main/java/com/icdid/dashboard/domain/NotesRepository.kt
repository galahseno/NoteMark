package com.icdid.dashboard.domain

import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.Result
import com.icdid.dashboard.domain.model.NoteDomain
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotes(): Flow<List<NoteDomain>>
    suspend fun getNote(id: NoteId): NoteDomain?
    suspend fun fetchNotes(): EmptyResult<DataError>
    suspend fun upsertNote(note: NoteDomain, isUpdate: Boolean):  Result<NoteId, DataError>
    suspend fun deleteNote(id: NoteId)
    suspend fun logout(): EmptyResult<DataError.Network>

    suspend fun syncNotesManually(): EmptyResult<DataError>
}