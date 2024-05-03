package com.adevspoon.domain.member.exception


import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

enum class MemberDomainErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    MEMBER_ALREADY_EXPIRED_REFRESH_TOKEN(DomainType.MEMBER code 403 no 0, "이미 만료된 리프레시 토큰입니다"),

    MEMBER_NOT_FOUND(DomainType.MEMBER code 404 no 0, "등록되지 않은 유저입니다"),
    MEMBER_BADGE_NOT_FOUND(DomainType.MEMBER code 404 no 1, "발급받지 않은 뱃지 등록");
}