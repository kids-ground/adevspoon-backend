package com.adevspoon.domain.post.techAnswer.domain

import com.adevspoon.domain.domain.LegacyBaseEntity
import com.adevspoon.domain.post.techAnswer.domain.enums.AnswerStatus
import com.adevspoon.domain.techQuestion.domain.Question
import com.adevspoon.domain.member.domain.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "answers", schema = "adevspoon")
class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "answer")
    var answer: String? = null,

    @NotNull
    @Column(name = "like_cnt", nullable = false)
    var likeCnt: Int? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "question_id", nullable = false)
    val question: Question? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User? = null,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: AnswerStatus? = null
): LegacyBaseEntity()