package com.adevspoon.domain.board.exception

import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

private val domain = DomainType.POST

enum class PostDomainErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    MINIMUM_LIKE_COUNT(domain code 400 no 0, "좋아요 수는 음수일 수 없습니다."),
    UNSUPPORTED_TYPE_FOR_CONTENT_ACCESS(domain code 400 no 1, "BOARD에서 지원하지 않는 타입입니다."),

    BOARD_TAG_NOT_FOUND(domain code 404 no 0, "등록되지 않은 태그입니다."),
    BOARD_POST_NOT_FOUND(domain code 404 no 1, "등록되지 않은 게시글입니다."),
    BOARD_COMMENT_NOT_FOUND(domain code 404 no 2, "등록되지 않은 댓글입니다."),

    BOARD_POST_EDIT_UNAUTHORIZED(domain code 403 no 0, "해당 게시글에 권한이 없습니다."),
    BOARD_COMMENT_EDIT_UNAUTHORIZED(domain code 403 no 1, "해당 댓글에 권한이 없습니다."),
    SELF_REPORT_NOT_ALLOWED(domain code 403 no 2, "자기 게시글 혹은 댓글은 신고할 수 없습니다."),

    ALREADY_REPORTED(domain code 409 no 0, "이미 신고가 접수된 게시글 혹은 댓글입니다."),

    BOARD_POST_INVALID_RETURN(domain code 500 no 0, "게시글 등록 중 오류가 발생했습니다.");
}