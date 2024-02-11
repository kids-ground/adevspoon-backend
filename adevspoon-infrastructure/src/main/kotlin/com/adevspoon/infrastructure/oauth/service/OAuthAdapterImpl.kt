package com.adevspoon.infrastructure.oauth.service

import com.adevspoon.infrastructure.oauth.client.AppleFeignClient
import com.adevspoon.infrastructure.oauth.client.KakaoFeignClient
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoResponse
import org.springframework.stereotype.Component

@Component
class OAuthAdapterImpl(
    private val kakaoFeignClient: KakaoFeignClient,
    private val appleFeignClient: AppleFeignClient,
): OAuthAdapter {
    override fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfoResponse {
        TODO("Not yet implemented")
    }
}