package com.icdid.dashboard.data.network

import com.icdid.core.data.network.delete
import com.icdid.core.data.network.get
import com.icdid.core.data.network.post
import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.Result
import com.icdid.core.domain.map
import com.icdid.dashboard.data.model.LogOutRequest
import com.icdid.dashboard.data.model.NoteDto
import com.icdid.dashboard.data.model.NotesResponseDto
import com.icdid.dashboard.data.model.toNoteDomain
import com.icdid.dashboard.data.model.toNoteRequest
import com.icdid.dashboard.domain.NoteId
import com.icdid.dashboard.domain.RemoteDataSource
import com.icdid.dashboard.domain.model.NoteDomain
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.http.HttpMethod

class KtorRemoteDataSource(
    private val httpClient: HttpClient
) : RemoteDataSource {
    override suspend fun getNotes(): Result<List<NoteDomain>, DataError.Network> {
        return httpClient.get<NotesResponseDto>(
            route = "/notes",
        ).map { noteDtos ->
            noteDtos.notes.map { it.toNoteDomain() }
        }
    }

    override suspend fun upsertNote(
        note: NoteDomain,
        isUpdate: Boolean
    ): Result<NoteDomain, DataError.Network> {
        return httpClient.post<NoteDto, NoteDto>(
            route = "/notes",
            body = note.toNoteRequest(),
            configure = {
                method = if (isUpdate) HttpMethod.Put else HttpMethod.Post
            }
        ).map {
            it.toNoteDomain()
        }
    }

    override suspend fun deleteNote(id: NoteId): EmptyResult<DataError.Network> {
        return httpClient.delete(
            route = "/notes/$id",
        )
    }

    override suspend fun logout(refreshToken: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LogOutRequest, Unit>(
            route = "/auth/logout",
            body = LogOutRequest(refreshToken),
        )
        httpClient.authProvider<BearerAuthProvider>()?.clearToken()
        return result
    }
}