package com.adevspoon.domain.techQuestion.domain

import com.adevspoon.domain.domain.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.io.Serializable

// FIXME - 빈 테이블 삭제 요망
@Entity
@Table(name = "QuestionDifficultyJudge", schema = "adevspoon")
class QuestionDifficultyJudge(
    @EmbeddedId
    val id: QuestionDifficultyJudgeId? = null,

    @NotNull
    @Column(name = "judge", nullable = false)
    val judge: Int? = null
): BaseEntity()

@Embeddable
class QuestionDifficultyJudgeId(
    @NotNull
    @Column(name = "userId", nullable = false)
    val userId: Long? = null,

    @NotNull
    @Column(name = "questionId", nullable = false)
    val questionId: Long? = null
): Serializable