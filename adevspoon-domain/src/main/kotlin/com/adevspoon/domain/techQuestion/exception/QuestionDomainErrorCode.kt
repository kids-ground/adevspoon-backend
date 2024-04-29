package com.adevspoon.domain.techQuestion.exception


import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

private val domain = DomainType.TECH_QUESTION

enum class QuestionDomainErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    QUESTION_NOT_OPENED(domain code 400 no 1, "발급받지 않은 Question 입니다"),
    QUESTION_EXHAUSTED(domain code 400 no 2, "발급 가능한 Question이 없습니다"),

    QUESTION_NOT_FOUND(domain code 404 no 0, "없는 Question ID로 조회 시도"),
    QUESTION_CATEGORY_NOT_FOUND(domain code 404 no 1, "없는 카테고리 ID로 등록 시도"),
    QUESTION_ANSWER_NOT_FOUND(domain code 404 no 2, "없는 Answer ID로 조회 시도"),
}