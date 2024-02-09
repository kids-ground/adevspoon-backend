package com.adevspoon.domain.oauthcore.dto

import com.adevspoon.domain.oauthcore.dto.OAuthType

data class OAuthUserInfoResponse(
    val type: OAuthType,
    val id: String
)
