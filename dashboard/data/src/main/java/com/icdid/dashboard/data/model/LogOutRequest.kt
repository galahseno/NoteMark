package com.icdid.dashboard.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LogOutRequest(
    val refreshToken: String
)
