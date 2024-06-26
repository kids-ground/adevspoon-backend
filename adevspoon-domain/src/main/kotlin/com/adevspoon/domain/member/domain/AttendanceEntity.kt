package com.adevspoon.domain.member.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "Attendance", schema = "adevspoon")
class AttendanceEntity(
    @EmbeddedId
    val attendanceId: AttendanceId? = null
)

@Embeddable
class AttendanceId(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity,

    @NotNull
    @Column(name = "attendTime", nullable = false)
    val attendTime: LocalDateTime
): Serializable