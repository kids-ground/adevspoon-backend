package com.adevspoon.domain.techQuestion.domain

import com.adevspoon.domain.domain.BaseEntity
import com.adevspoon.domain.member.domain.User
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable

// 유저가 질문 발급받기로 선택한 카테고리
@Entity
@Table(name = "UserCustomizedQuestionCategory", schema = "adevspoon")
class UserCustomizedQuestionCategory(
    @EmbeddedId
    val id: UserCustomizedQuestionCategoryId
): BaseEntity()

@Embeddable
class UserCustomizedQuestionCategoryId(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "categoryId", nullable = false)
    val category: QuestionCategory? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", nullable = false)
    val user: User? = null
): Serializable