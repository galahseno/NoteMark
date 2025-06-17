package com.icdid.core.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
