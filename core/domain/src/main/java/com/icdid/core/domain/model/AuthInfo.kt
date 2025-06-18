package com.icdid.core.domain.model

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val username: String
)