package com.adevspoon.domain.oauth.dto

data class OAuthUserInfoRequest(
    val type: OAuthType,
    val token: String
)
