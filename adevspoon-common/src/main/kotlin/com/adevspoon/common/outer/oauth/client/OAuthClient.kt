package com.adevspoon.common.outer.oauth.client

import com.adevspoon.common.outer.oauth.dto.OAuthUserInfoRequest
import com.adevspoon.common.outer.oauth.dto.OAuthUserInfoResponse

interface OAuthClient {
    fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfoResponse
}