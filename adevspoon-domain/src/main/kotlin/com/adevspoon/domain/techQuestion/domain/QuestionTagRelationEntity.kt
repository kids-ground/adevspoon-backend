package com.adevspoon.domain.techQuestion.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable

// FIXME - 현재 사용중인 것 같지 않음
@Entity
@Table(name = "questionTagRelation", schema = "adevspoon")
class QuestionTagRelationEntity(
    @EmbeddedId
    val questionTagRelationId: QuestionTagRelationId? = null
)

@Embeddable
class QuestionTagRelationId(
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "question_id", nullable = false)
    val question: QuestionEntity? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tag_id", nullable = false)
    val tag: TagEntity? = null
): Serializable