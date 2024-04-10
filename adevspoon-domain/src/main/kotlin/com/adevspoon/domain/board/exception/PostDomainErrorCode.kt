package com.adevspoon.domain.board.exception

import com.adevspoon.common.exception.AdevspoonErrorCode

enum class PostDomainErrorCode(
    override val status: Int,
    override val code: Int,
    override val message: String,
): AdevspoonErrorCode {
    BOARD_TAG_NOT_FOUND(404, 404_001_000, "등록되지 않은 태그입니다.");
}