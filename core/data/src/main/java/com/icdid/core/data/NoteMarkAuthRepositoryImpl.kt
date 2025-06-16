package com.icdid.core.data

import com.icdid.core.data.model.request.LoginRequest
import com.icdid.core.data.model.request.RegisterRequest
import com.icdid.core.data.model.response.AuthenticationResponse
import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.NoteMarkAuthRepository
import com.icdid.core.domain.Result
import com.icdid.core.domain.asEmptyDataResult
import io.ktor.client.HttpClient
import post

class NoteMarkAuthRepositoryImpl(
    private val httpClient: HttpClient,
) : NoteMarkAuthRepository {
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, AuthenticationResponse>(
            route = "/auth/login",
            body = LoginRequest(email, password)
        )

        if(result is Result.Success<*>) {
            // TODO: Data store, save Auth Info
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(username: String, email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/auth/register",
            body = RegisterRequest(username, email, password)
        )
    }
}