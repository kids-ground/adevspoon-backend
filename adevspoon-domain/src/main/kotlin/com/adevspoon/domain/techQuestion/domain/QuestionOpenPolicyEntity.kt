package com.adevspoon.domain.techQuestion.domain

import jakarta.persistence.*
import java.time.LocalDate

// FIXME: 모든 이용자가 매일 같은 질문을 발급받았을때 사용
@Entity
@Table(name = "question_open_policy")
class QuestionOpenPolicyEntity(
    @Id
    @Column(name = "open_date", nullable = false)
    val id: LocalDate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    val question: QuestionEntity? = null
)