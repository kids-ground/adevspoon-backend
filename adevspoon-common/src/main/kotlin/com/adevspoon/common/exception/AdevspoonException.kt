package com.adevspoon.common.exception

class AdevspoonException(
    val code: Int,
    val status: Int,
    override val message: String?,
) : RuntimeException(message)