package com.icdid.core.domain

interface NoteMarkAuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(username: String, email: String, password: String): EmptyResult<DataError.Network>
}