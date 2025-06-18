package com.icdid.core.data.model

import com.icdid.core.domain.model.AuthInfo
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val accessToken: String = "",
    val refreshToken: String = "",
    val username: String = "",
)

fun AuthInfoSerializable.toAuthInfo() = AuthInfo(
    accessToken = accessToken,
    refreshToken = refreshToken,
    username = username
)