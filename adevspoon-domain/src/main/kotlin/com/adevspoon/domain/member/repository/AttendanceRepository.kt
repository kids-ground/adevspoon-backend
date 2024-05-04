package com.adevspoon.domain.member.repository

import com.adevspoon.domain.member.domain.AttendanceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Date

interface AttendanceRepository: JpaRepository<AttendanceEntity, Long> {
    @Query("SELECT DATE(a.attendanceId.attendTime) " +
            "FROM AttendanceEntity a " +
            "WHERE a.attendanceId.user.id = :userId " +
            "ORDER BY DATE(a.attendanceId.attendTime) DESC " +
            "Limit 2")
    fun findAttendanceDateList(userId: Long): List<Date>
}