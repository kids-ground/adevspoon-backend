package com.adevspoon.domain.member.exception


import com.adevspoon.common.exception.AdevspoonErrorCode

enum class MemberDomainErrorCode(
    override val status: Int,
    override val code: Int,
    override val message: String,
): AdevspoonErrorCode {
    MEMBER_NOT_FOUND(404, 404_001_000, "등록되지 않은 유저입니다"),
    MEMBER_BADGE_NOT_FOUND(404, 404_001_001, "발급받지 않은 뱃지 등록");
}