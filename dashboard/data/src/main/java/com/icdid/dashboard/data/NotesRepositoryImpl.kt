package com.icdid.dashboard.data

import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.Result
import com.icdid.core.domain.SessionStorage
import com.icdid.core.domain.asEmptyDataResult
import com.icdid.core.domain.map
import com.icdid.dashboard.domain.LocalDataSource
import com.icdid.dashboard.domain.NoteId
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.RemoteDataSource
import com.icdid.dashboard.domain.model.NoteDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NotesRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage
) : NotesRepository {
    override fun getNotes(): Flow<List<NoteDomain>> {
        return localDataSource.getNotes()
    }

    override suspend fun getNote(id: NoteId): NoteDomain? {
        return localDataSource.getNote(id)
    }

    override suspend fun fetchNotes(): EmptyResult<DataError> {
        val result = remoteDataSource.getNotes()
        return when (result) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertNotes(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertNote(note: NoteDomain, isUpdate: Boolean): Result<NoteId, DataError> {
        return applicationScope.async {
            val remoteResult = remoteDataSource.upsertNote(note, isUpdate)

            if (remoteResult is Result.Error) {
                // TODO handle schedule sync when error post api
            }

            return@async remoteResult.map { it.id }
        }.await()
    }

    override suspend fun upsertNoteLocally(note: NoteDomain, isUpdate: Boolean): Result<NoteId, DataError> {
        return localDataSource.upsertNote(note)
    }


    override suspend fun deleteNote(id: NoteId) {
        localDataSource.deleteNote(id)

        val remoteResult = applicationScope.async {
            remoteDataSource.deleteNote(id)
        }.await()

        if (remoteResult is Result.Error) {
            // TODO handle schedule sync when error delete api
        }
    }

    override suspend fun deleteAllNotes() {
        localDataSource.deleteAllNotes()
    }

    override suspend fun logout(): EmptyResult<DataError.Network> {
        val remoteResult = applicationScope.async {
            remoteDataSource.logout(sessionStorage.get().first().refreshToken)
        }.await()

        if(remoteResult is Result.Error) {
            return remoteResult.asEmptyDataResult()
        } else {
            applicationScope.async {
                localDataSource.deleteAllNotes()
                sessionStorage.clear()
            }.await()
        }
        return Result.Success(Unit)
    }
}