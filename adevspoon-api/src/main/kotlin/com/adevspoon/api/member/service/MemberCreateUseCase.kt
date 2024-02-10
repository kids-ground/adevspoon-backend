package com.adevspoon.api.member.service

import com.adevspoon.api.common.annotation.UseCase
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoResponse
import org.springframework.transaction.annotation.Transactional

@UseCase
class MemberCreateUseCase() {

    @Transactional
    fun execute(oauthInfo: OAuthUserInfoResponse) {

    }
}