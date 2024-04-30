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
    QUESTION_ANSWER_REPORT_NOT_ALLOWED(domain code 400 no 3, "해당 게시글은 신고할 수 없습니다"),
    QUESTION_ANSWER_REPORT_ALREADY_EXIST(domain code 400 no 4, "해당 게시글을 이미 신고했습니다"),
    MINIMUM_LIKE_COUNT(domain code 400 no 5, "좋아요 수는 음수일 수 없습니다."),

    QUESTION_ANSWER_EDIT_UNAUTHORIZED(domain code 403 no 0, "해당 댓글에 수정 권한이 없습니다."),

    QUESTION_NOT_FOUND(domain code 404 no 0, "없는 Question ID로 조회 시도"),
    QUESTION_CATEGORY_NOT_FOUND(domain code 404 no 1, "없는 카테고리 ID로 등록 시도"),
    QUESTION_ANSWER_NOT_FOUND(domain code 404 no 2, "없는 Answer ID로 조회 시도"),

    QUESTION_ANSWER_INVALID_RETURN(domain code 500 no 0, "Answer 등록 중 오류가 발생했습니다");
}