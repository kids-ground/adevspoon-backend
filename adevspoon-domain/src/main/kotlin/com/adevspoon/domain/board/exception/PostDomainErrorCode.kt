package com.adevspoon.domain.board.exception

import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

enum class PostDomainErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    BOARD_TAG_NOT_FOUND(DomainType.POST code 404 no 0, "등록되지 않은 태그입니다.");
}