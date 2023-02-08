package com.example.iismobile.common.encryption

import com.example.iismobile.common.encryption.base.IEncryptionLayer

object NullEncryptionLayer : IEncryptionLayer {
    override fun encrypt(data: ByteArray): ByteArray {
        return data
    }

    override fun decrypt(encryptedData: ByteArray): ByteArray {
        return encryptedData
    }

}