package com.adevspoon.domain.techQuestion.domain.enums

import com.adevspoon.domain.common.entity.LegacyEntityEnum
import com.adevspoon.domain.config.converter.LegacyEntityEnumConverter
import jakarta.persistence.Converter

enum class QuestionCategoryTopic: LegacyEntityEnum {
    CS, LANGUAGE, TECH;

    @Converter(autoApply = true)
    class QuestionCategoryTopicConverter: LegacyEntityEnumConverter<QuestionCategoryTopic>(QuestionCategoryTopic::class.java, nullable = false)
}