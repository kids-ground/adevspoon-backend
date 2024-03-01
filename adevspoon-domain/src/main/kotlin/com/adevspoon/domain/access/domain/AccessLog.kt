package com.adevspoon.domain.access.domain

import com.adevspoon.domain.access.domain.enums.AccessLogType
import com.adevspoon.domain.domain.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.io.Serializable

// 조회 로그
@Entity
@Table(name = "AccessLog", schema = "adevspoon")
class AccessLog (
    @EmbeddedId
    val id: AccessLogId
): BaseEntity()

@Embeddable
class AccessLogId(
    @Size(max = 50)
    @NotNull
    @Column(name = "type", nullable = false, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    val type: AccessLogType,

    @NotNull
    @Column(name = "userId", nullable = false)
    val userId: Long,

    @NotNull
    @Column(name = "targetId", nullable = false)
    val targetId: Long,
) : Serializable