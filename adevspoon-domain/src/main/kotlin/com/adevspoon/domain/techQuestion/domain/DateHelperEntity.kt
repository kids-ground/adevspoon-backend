package com.adevspoon.domain.techQuestion.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "DateHelper", schema = "adevspoon")
class DateHelperEntity(
    @Id
    @Column(name = "Date", nullable = false)
    var id: LocalDateTime? = null
)