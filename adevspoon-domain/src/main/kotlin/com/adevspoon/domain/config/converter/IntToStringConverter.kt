package com.adevspoon.domain.config.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class IntToStringConverter: AttributeConverter<Int, String> {
    override fun convertToDatabaseColumn(attribute: Int?): String? {
        return attribute?.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): Int? {
        return dbData?.toInt()
    }
}