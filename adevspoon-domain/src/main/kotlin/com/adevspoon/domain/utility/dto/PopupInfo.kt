package com.adevspoon.domain.utility.dto

import java.time.LocalDate

data class PopupInfo(
    val id: Long,
    val name: String? = null,
    val imageUrl: String,
    val url: String? = null,
    val openDate: LocalDate,
    val closeDate: LocalDate? = null
)
