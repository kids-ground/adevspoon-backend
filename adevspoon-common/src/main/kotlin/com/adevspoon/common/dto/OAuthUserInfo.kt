package com.adevspoon.common.dto

import com.adevspoon.common.enums.SocialType

data class OAuthUserInfo(
    val type: SocialType,
    val id: String,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val thumbnailImageUrl: String? = null,
    val email: String? = null,
)