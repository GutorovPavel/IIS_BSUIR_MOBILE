package com.example.iismobile.login.data

data class UserCache(
    val token: String,
    val credentials: Pair<String, String>? = null
)