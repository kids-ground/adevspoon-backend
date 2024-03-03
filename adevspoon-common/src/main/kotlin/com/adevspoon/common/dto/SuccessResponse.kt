package com.adevspoon.common.dto

data class SuccessResponse<T>(
    val code: Int = 200,
    val message: String = "Success",
    val data: T
)