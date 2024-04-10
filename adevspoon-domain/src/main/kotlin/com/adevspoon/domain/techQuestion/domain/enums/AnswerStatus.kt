package com.adevspoon.domain.techQuestion.domain.enums

import com.adevspoon.domain.common.entity.LegacyEntityEnum
import com.adevspoon.domain.common.converter.LegacyEntityEnumConverter
import jakarta.persistence.Converter

enum class AnswerStatus: LegacyEntityEnum {
    PRIVATE, PUBLIC;

    @Converter(autoApply = true)
    class AnswerStatusConverter: LegacyEntityEnumConverter<AnswerStatus>(AnswerStatus::class.java)
}