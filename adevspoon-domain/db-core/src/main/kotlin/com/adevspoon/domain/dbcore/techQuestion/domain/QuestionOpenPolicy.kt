package com.adevspoon.domain.dbcore.techQuestion.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "question_open_policy")
class QuestionOpenPolicy(
    @Id
    @Column(name = "open_date", nullable = false)
    val id: LocalDate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    val question: Question? = null
)