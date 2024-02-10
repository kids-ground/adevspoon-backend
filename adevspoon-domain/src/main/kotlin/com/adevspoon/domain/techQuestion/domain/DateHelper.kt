package com.adevspoon.domain.techQuestion.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.Instant
import java.time.LocalDateTime

@Entity
class DateHelper(
    @Id
    @Column(name = "Date", nullable = false)
    var id: LocalDateTime? = null
)