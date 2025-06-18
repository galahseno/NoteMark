package com.icdid.core.data.network

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
