package com.icdid.core.data.network

import com.icdid.core.data.BuildConfig
import com.icdid.core.domain.Result
import com.icdid.core.domain.SessionStorage
import com.icdid.core.domain.model.AuthInfo
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {
    fun build(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                headers {
                    append("X-User-Email", BuildConfig.CAMPUS_EMAIL)
                }
            }
            install(Auth) {
                bearer {
                   loadTokens {
                       val info = sessionStorage.get().first()
                       BearerTokens(
                           accessToken = info.accessToken,
                           refreshToken = info.refreshToken,
                       )
                   }
                    refreshTokens {
                        val info = sessionStorage.get().first()

                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = "/auth/refresh",
                            body = AccessTokenRequest(
                                refreshToken = info.refreshToken,
                            )
                        )

                        when (response) {
                            is Result.Success -> {
                                val newAuthInfo = AuthInfo(
                                    accessToken = response.data.accessToken,
                                    refreshToken = response.data.refreshToken,
                                    username = info.username
                                )
                                sessionStorage.set(newAuthInfo)

                                BearerTokens(
                                    accessToken = newAuthInfo.accessToken,
                                    refreshToken = newAuthInfo.refreshToken
                                )
                            }

                            else -> {
                                val newAuthInfo = AuthInfo(
                                    accessToken = "",
                                    refreshToken = "",
                                    username = ""
                                )
                                sessionStorage.set(newAuthInfo)

                                BearerTokens(
                                    accessToken = "",
                                    refreshToken = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}