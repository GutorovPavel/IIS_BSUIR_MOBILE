package com.example.iismobile.common.data

import java.lang.RuntimeException

data class InternalException(val msg: String) : RuntimeException()