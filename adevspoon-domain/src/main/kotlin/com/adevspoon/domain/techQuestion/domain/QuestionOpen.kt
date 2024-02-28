package com.adevspoon.domain.techQuestion.domain

import com.adevspoon.domain.domain.BaseEntity
import com.adevspoon.domain.post.techAnswer.domain.Answer
import com.adevspoon.domain.member.domain.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime


// FIXME - 레거시? 과거 질문발급 알고리즘 관련 테이블인지 확인필요
@Entity
@Table(name = "question_open")
class QuestionOpen(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

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

    @NotNull
    @Column(name = "open_date", nullable = false)
    var openDate: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "answer_id")
    var answer: Answer? = null
): BaseEntity()