package com.icdid.auth.data

import com.icdid.auth.data.model.LoginRequest
import com.icdid.auth.data.model.RegisterRequest
import com.icdid.auth.data.model.response.LoginResponse
import com.icdid.auth.domain.AuthRepository
import com.icdid.core.data.network.post
import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult
import com.icdid.core.domain.Result
import com.icdid.core.domain.asEmptyDataResult
import com.icdid.core.domain.model.AuthInfo
import com.icdid.core.domain.session.SessionStorage
import com.icdid.core.domain.session.UserSettings
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage,
    private val userSettings: UserSettings,
) : AuthRepository {
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/auth/login",
            body = LoginRequest(email, password)
        )

        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    username = result.data.username
                )
            )
            userSettings.getUserId(result.data.username)
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/auth/register",
            body = RegisterRequest(username, email, password)
        )
    }
}