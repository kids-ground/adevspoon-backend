package com.adevspoon.domain.techQuestion.exception


import com.adevspoon.common.exception.AdevspoonErrorCode

enum class QuestionDomainErrorCode(
    override val status: Int,
    override val code: Int,
    override val message: String,
): AdevspoonErrorCode {
    QUESTION_CATEGORY_NOT_FOUND(404, 404_002_000, "등록되지 않은 카테고리 등록");
}