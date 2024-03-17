package com.adevspoon.domain.techQuestion.exception

import com.adevspoon.common.dto.ErrorInfo
import com.adevspoon.common.exception.AdevspoonErrorCode

enum class QuestionDomainErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    QUESTION_CATEGORY_NOT_FOUND(404, 404_002_000, "등록되지 않은 카테고리 등록");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}