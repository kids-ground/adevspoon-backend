package com.adevspoon.domain.techQuestion.exception


import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

enum class QuestionDomainErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    QUESTION_CATEGORY_NOT_FOUND(DomainType.TECH_QUESTION code 404 no 0, "등록되지 않은 카테고리 등록");
}