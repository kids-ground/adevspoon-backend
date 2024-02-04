package com.adevspoon.domain.user.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.io.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "Attendance", schema = "adevspoon")
class Attendance(
    @EmbeddedId
    val attendanceId: AttendanceId? = null
)

@Embeddable
class AttendanceId(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    val user: User? = null,

    @NotNull
    @Column(name = "attendTime", nullable = false)
    val attendTime: LocalDateTime? = null
): Serializable