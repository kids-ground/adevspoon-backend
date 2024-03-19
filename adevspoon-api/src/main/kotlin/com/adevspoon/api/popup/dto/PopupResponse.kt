package com.adevspoon.api.popup.dto

import java.time.LocalDate

data class PopupResponse(
    val id: Long,
    val name: String? = null,
    val imageUrl: String,
    val url: String? = null,
    val openDate: LocalDate,
    val closeDate: LocalDate? = null
)
