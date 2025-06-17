package com.icdid.auth.domain

import com.icdid.core.domain.DataError
import com.icdid.core.domain.EmptyResult

interface NoteMarkAuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(username: String, email: String, password: String): EmptyResult<DataError.Network>
}