package com.adevspoon.domain.post.board.exception

import com.adevspoon.common.dto.ErrorInfo
import com.adevspoon.common.exception.AdevspoonErrorCode

enum class PostDomainErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    BOARD_TAG_NOT_FOUND(404, 404_001_000, "등록되지 않은 태그입니다.");


    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}