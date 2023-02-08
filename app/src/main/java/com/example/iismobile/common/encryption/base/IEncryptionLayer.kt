package com.example.iismobile.common.encryption.base

interface IEncryptionLayer {

    fun encrypt(data: ByteArray): ByteArray

    fun decrypt(encryptedData: ByteArray): ByteArray

}