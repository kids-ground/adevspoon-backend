package com.adevspoon.domain.techQuestion.domain

import com.adevspoon.domain.common.entity.LegacyBaseEntity
import com.adevspoon.domain.techQuestion.domain.enums.AnswerStatus
import com.adevspoon.domain.member.domain.UserEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "answers", schema = "adevspoon")
class AnswerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "answer")
    var answer: String? = null,

    @NotNull
    @Column(name = "like_cnt", nullable = false)
    var likeCnt: Int = 0,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "question_id", nullable = false)
    val question: QuestionEntity,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,

    @Column(name = "status", columnDefinition="ENUM('private','public')")
    var status: AnswerStatus = AnswerStatus.PUBLIC
): LegacyBaseEntity()