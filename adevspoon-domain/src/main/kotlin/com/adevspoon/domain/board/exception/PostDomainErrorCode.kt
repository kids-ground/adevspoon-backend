package com.adevspoon.domain.board.exception

import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

private val domain = DomainType.POST

enum class PostDomainErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    BOARD_TAG_NOT_FOUND(domain code 404 no 0, "등록되지 않은 태그입니다."),
    BOARD_POST_NOT_FOUND(domain code 404 no 1, "등록되지 않은 게시글입니다."),
    BOARD_POST_EDIT_UNAUTHORIZED(domain code 403 no 0, "해당 게시글에 권한이 없습니다.");
}