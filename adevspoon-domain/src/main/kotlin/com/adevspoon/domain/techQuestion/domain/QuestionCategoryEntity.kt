package com.adevspoon.domain.techQuestion.domain

import com.adevspoon.domain.techQuestion.domain.enums.QuestionCategoryTopic
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "question_category", schema = "adevspoon")
class QuestionCategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,

    @Size(max = 255)
    @Column(name = "category", nullable = false)
    val category: String,

    @NotNull
    @Column(name = "topic", columnDefinition="ENUM('cs','language','tech')", nullable = false)
    val topic: QuestionCategoryTopic,

    @Size(max = 16)
    @NotNull
    @Column(name = "subtitle", nullable = false, length = 16)
    var subtitle: String? = null,

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    var description: String? = null,

    @Column(name = "iconUrl")
    var iconUrl: String,

    @Size(max = 6)
    @NotNull
    @Column(name = "backgroundColor", nullable = false, length = 6)
    var backgroundColor: String,

    @Size(max = 6)
    @NotNull
    @Column(name = "accentColor", nullable = false, length = 6)
    var accentColor: String,

    @Size(max = 6)
    @NotNull
    @Column(name = "iconColor", nullable = false, length = 6)
    var iconColor: String
)