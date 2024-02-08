package com.adevspoon.api.member.dto.request

data class SocialLoginRequest(
    val oauthToken: String,
    val loginType: String,
)
