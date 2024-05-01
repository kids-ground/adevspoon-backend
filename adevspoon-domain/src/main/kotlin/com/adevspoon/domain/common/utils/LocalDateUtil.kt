package com.adevspoon.domain.common.utils

import java.time.LocalDate
import java.time.LocalDateTime


fun LocalDate.isToday(): Boolean {
    return this.compareTo(LocalDate.now()) == 0
}

fun LocalDateTime.isToday(): Boolean {
    return this.toLocalDate().compareTo(LocalDate.now()) == 0
}