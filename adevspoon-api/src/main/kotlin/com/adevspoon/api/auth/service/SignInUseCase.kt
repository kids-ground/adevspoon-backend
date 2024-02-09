package com.adevspoon.api.auth.service

import com.adevspoon.api.common.annotation.UseCase
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.service.MemberCreateUseCase


@UseCase
class SignInUseCase(
    private val memberCreateUseCase: MemberCreateUseCase,
//    private val oAuthClient: OAuthClient
) {

    fun execute(loginRequest: SocialLoginRequest){

    }
}