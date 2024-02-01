package com.adevspoon.domain.user

import com.adevspoon.domain.user.enums.UserOAuth
import com.adevspoon.domain.user.enums.UserProfileBelt
import com.adevspoon.domain.user.enums.UserStatus
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: UserStatus? = null

    @Column(name = "oAuth")
    @Enumerated(EnumType.STRING)
    var oAuth: UserOAuth? = null

    @Column(name = "kakao_id")
    var kakaoId: Long? = null

    @Size(max = 255)
    @Column(name = "apple_id")
    var appleId: String? = null

    @Size(max = 255)
    @Column(name = "refresh_token")
    var refreshToken: String? = null

    @Size(max = 200)
    @Column(name = "fcm_token", length = 200)
    var fcmToken: String? = null

    @Size(max = 255)
    @Column(name = "nickname")
    var nickname: String? = null

    @Column(name = "profile_img")
    var profileImg: String? = null

    @Column(name = "thumbnail_img")
    var thumbnailImg: String? = null

    @NotNull
    @Column(name = "question_cnt", nullable = false)
    var questionCnt: Int? = null

    @NotNull
    @Column(name = "answer_cnt", nullable = false)
    var answerCnt: Int? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    var email: String? = null

    @NotNull
    @Column(name = "profileBelt", nullable = false)
    @Enumerated(EnumType.STRING)
    var profileBelt: UserProfileBelt? = null

    @Size(max = 1)
    @Column(name = "representativeBadge", length = 1)
    var representativeBadge: String? = null

    @Size(max = 50)
    @Column(name = "statusMessage", length = 50)
    var statusMessage: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "careerDescription", nullable = false)
    var careerDescription: String? = null
}