package com.adevspoon.domain.member.service

import com.adevspoon.domain.annotation.DomainIntegrationTest
import com.adevspoon.domain.member.domain.BadgeEntity
import com.adevspoon.domain.member.domain.UserBadgeAcheiveEntity
import com.adevspoon.domain.member.domain.UserBadgeAcheiveId
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.dto.request.MemberUpdateRequireDto
import com.adevspoon.domain.member.repository.BadgeRepository
import com.adevspoon.domain.member.repository.UserBadgeAcheiveRepository
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.service.QuestionDomainService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired

@DomainIntegrationTest
class MemberDomainServiceIntegrationTest {

    @Autowired lateinit var memberDomainService: MemberDomainService
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var badgeRepository: BadgeRepository
    @Autowired lateinit var userBadgeAcheiveRepository: UserBadgeAcheiveRepository
    @Autowired lateinit var questionDomainService: QuestionDomainService


    @Nested
    inner class UpdateMemberProfile {
        @BeforeEach
        fun setup() {
            val user = UserEntity(
                id = 1,
                nickname = "nickname",
                fcmToken = "fcmToken",
                profileImg = "profileImg",
                thumbnailImg = "thumbnailImg",
            )
            val badge = BadgeEntity(id = 1, name = "badge")
            val userBadge = UserBadgeAcheiveEntity(UserBadgeAcheiveId(user, badge))
            userRepository.save(user)
            badgeRepository.save(badge)
            userBadgeAcheiveRepository.save(userBadge)
        }

        @Test
        fun `SUCCESS - 플레인 데이터 업데이트 성공`() {
            // given
            val updateInfo = MemberUpdateRequireDto(
                memberId = 1,
                nickname = "newNickname",
                fcmToken = "newFcmToken",
                profileImageUrl = "newProfileImg",
            )

            // when
            val result = memberDomainService.updateMemberProfile(updateInfo)

            // then
            assertThat(result.memberId).isEqualTo(1)
            assertThat(result.nickname).isEqualTo("newNickname")
        }
    }
}