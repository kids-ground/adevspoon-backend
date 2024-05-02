package com.adevspoon.domain.member.domain

import com.adevspoon.domain.common.entity.LegacyBaseEntity
import com.adevspoon.domain.common.converter.IntToStringConverter
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.domain.member.domain.enums.UserProfileBelt
import com.adevspoon.domain.member.domain.enums.UserStatus
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "status" , columnDefinition="ENUM('active','exit')")
    var status: UserStatus = UserStatus.ACTIVE,

    @Column(name = "oAuth" , columnDefinition="ENUM('kakao','apple')")
    var oAuth: UserOAuth? = null,

    @Column(name = "kakao_id")
    var kakaoId: Long? = null,

    @Size(max = 255)
    @Column(name = "apple_id")
    var appleId: String? = null,

    @Size(max = 255)
    @Column(name = "refresh_token")
    var refreshToken: String? = null,

    @Size(max = 200)
    @Column(name = "fcm_token", length = 200)
    var fcmToken: String? = null,

    @Size(max = 255)
    @Column(name = "nickname")
    var nickname: String? = null,

    @Column(name = "profile_img")
    var profileImg: String? = null,

    @Column(name = "thumbnail_img")
    var thumbnailImg: String? = null,

    @NotNull
    @Column(name = "question_cnt", nullable = false)
    var questionCnt: Int = 0,

    @NotNull
    @Column(name = "answer_cnt", nullable = false)
    var answerCnt: Int = 0,

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    var email: String = "",

    @NotNull
    @Column(name = "profileBelt" , columnDefinition="ENUM('none','passionate')" ,nullable = false)
    var profileBelt: UserProfileBelt = UserProfileBelt.NONE,

    @Column(name = "representativeBadge", length = 1)
    @Convert(converter = IntToStringConverter::class)
    var representativeBadge: Int? = null,

    @Size(max = 50)
    @Column(name = "statusMessage", length = 50)
    var statusMessage: String? = null,

    @Size(max = 255)
    @NotNull
    @Column(name = "careerDescription", nullable = false)
    var careerDescription: String = ""
): LegacyBaseEntity() {
    fun increaseQuestionCnt() {
        questionCnt += 1
    }

    fun increaseAnswerCnt() {
        answerCnt += 1
    }
}