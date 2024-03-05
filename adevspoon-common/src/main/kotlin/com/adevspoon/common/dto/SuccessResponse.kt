package com.adevspoon.common.dto

data class SuccessResponse(
    val code: Int = 200,
    val message: String = "Success",
    val data: Any?
)