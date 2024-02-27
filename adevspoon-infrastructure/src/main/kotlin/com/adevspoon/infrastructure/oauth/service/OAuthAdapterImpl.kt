package com.adevspoon.infrastructure.oauth.service

import com.adevspoon.infrastructure.common.annotation.Adapter
import com.adevspoon.infrastructure.oauth.client.AppleFeignClient
import com.adevspoon.infrastructure.oauth.client.KakaoFeignClient
import com.adevspoon.infrastructure.oauth.dto.OAuthType
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoResponse
import org.springframework.stereotype.Component

@Adapter
class OAuthAdapterImpl(
    private val kakaoFeignClient: KakaoFeignClient,
    private val appleKeyService: AppleKeyService,
): OAuthAdapter {
    override fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfoResponse =
        when (oAuthUserInfoRequest.type) {
            OAuthType.KAKAO -> getKakaoUserInfo(oAuthUserInfoRequest.kakaoAccessToken)
            OAuthType.APPLE -> getAppleUserInfo(oAuthUserInfoRequest.appleIdentityToken)
        }

    private fun getKakaoUserInfo(accessToken: String?): OAuthUserInfoResponse =
        accessToken?.let {
            OAuthUserInfoResponse(
                id = "${kakaoFeignClient.getUserProfile("Bearer $accessToken").id}",
                type = OAuthType.KAKAO
            )
        } ?: throw IllegalArgumentException("accessToken is null")

    private fun getAppleUserInfo(identityToken: String?): OAuthUserInfoResponse =
        identityToken?.let {
            OAuthUserInfoResponse(
                id = appleKeyService.getIdentityKey(it),
                type = OAuthType.APPLE
            )
        } ?: throw IllegalArgumentException("identityToken is null")
}