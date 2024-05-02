package com.adevspoon.domain.techQuestion.domain

import com.adevspoon.domain.common.entity.LegacyBaseEntity
import com.adevspoon.domain.common.converter.IntToStringConverter
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "questions", schema = "adevspoon")
class QuestionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @NotNull
    @Column(name = "question", nullable = false)
    val question: String,

    @Column(name = "difficulty", columnDefinition="ENUM('0','1','2','3')")
    @Convert(converter = IntToStringConverter::class)
    var difficulty: Int? = null,

    @Column(name = "study_link")
    var studyLink: String? = null,

    @Size(max = 255)
    @Column(name = "notion_id")
    var notionId: String? = null,

    // QuestionCategory
    @Column(name = "category_id", nullable = false)
    val categoryId: Long,

    @Column(name = "sequence")
    var sequence: Int? = null
): LegacyBaseEntity()