package com.adevspoon.domain.common.exception


import com.adevspoon.common.exception.AdevspoonErrorCode

enum class DomainCommonError(
    override val status: Int,
    override  val code: Int,
    override  val message: String,
): AdevspoonErrorCode {
    // code = {status}_{service|domain}_{num}
    INVALID_ATTRIBUTE(500, 500_000_001, "유효하지 않은 값 사용");
}