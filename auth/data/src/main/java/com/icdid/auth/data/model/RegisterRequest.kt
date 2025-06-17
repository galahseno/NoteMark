package com.icdid.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    val username: String,
    val email: String,
    val password: String,
)