package com.icdid.dashboard.domain

import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.Result
import com.icdid.dashboard.domain.model.NoteDomain

interface RemoteDataSource {
    suspend fun getNotes(): Result<List<NoteDomain>, DataError.Network>
    suspend fun upsertNote(note: NoteDomain, isUpdate: Boolean): Result<NoteDomain, DataError.Network>
    suspend fun deleteNote(id: NoteId): EmptyResult<DataError.Network>
    suspend fun logout(refreshToken: String): EmptyResult<DataError.Network>
}