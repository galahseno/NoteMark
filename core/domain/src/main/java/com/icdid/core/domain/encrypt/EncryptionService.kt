package com.icdid.core.domain.encrypt

interface EncryptionService {
    fun encrypt(data: String): Pair<String, String>
    fun decrypt(encryptedData: String, iv: String): String
    fun encryptDataStore(bytes: ByteArray): ByteArray
    fun decryptDataStore(bytes: ByteArray): ByteArray

    fun hashKey(key: String): String
}