package com.adevspoon.common.exception


enum class CommonErrorCode(
    override val status: Int,
    override val code: Int,
    override val message: String,
): AdevspoonErrorCode {
    // code = {status}_{service|domain}_{num}
    BAD_REQUEST(400, 400_000_000, "잘못된 요청입니다"),

    UNAUTHORIZED(401, 401_000_000, "잘못된 인증 정보입니다"),

    FORBIDDEN(403, 403_000_000, "접근 권한이 없습니다"),

    NOT_FOUND(404, 404_000_000, "요청 정보를 찾을 수 없습니다"),

    INTERNAL_SERVER_ERROR(500, 500_000_000, "서버 내부 오류입니다. 관리자에게 문의하세요");
}