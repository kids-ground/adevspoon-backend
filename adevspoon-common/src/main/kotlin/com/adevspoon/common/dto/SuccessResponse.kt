package com.adevspoon.common.dto

import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class SuccessResponse(
    val code: Int = 200,
    val message: String = "Success",
    val data: Any?
)