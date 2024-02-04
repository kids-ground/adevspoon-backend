package com.adevspoon.domain.techQuestion.domain

import com.adevspoon.domain.techQuestion.domain.enums.QuestionCategoryTopic
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "question_category", schema = "adevspoon")
class QuestionCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,

    @Size(max = 255)
    @Column(name = "category")
    val category: String? = null,

    @NotNull
    @Column(name = "topic", nullable = false)
    @Enumerated(EnumType.STRING)
    val topic: QuestionCategoryTopic? = null,

    @Size(max = 16)
    @NotNull
    @Column(name = "subtitle", nullable = false, length = 16)
    var subtitle: String? = null,

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    var description: String? = null,

    @Size(max = 6)
    @NotNull
    @Column(name = "backgroundColor", nullable = false, length = 6)
    var backgroundColor: String? = null,

    @Column(name = "iconUrl")
    var iconUrl: String? = null,

    @Size(max = 6)
    @NotNull
    @Column(name = "accentColor", nullable = false, length = 6)
    var accentColor: String? = null,

    @Size(max = 6)
    @NotNull
    @Column(name = "iconColor", nullable = false, length = 6)
    var iconColor: String? = null
)