package com.adevspoon.infrastructure.oauth.service

import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import com.adevspoon.common.dto.OAuthUserInfo

interface OAuthAdapter {
    fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfo
}