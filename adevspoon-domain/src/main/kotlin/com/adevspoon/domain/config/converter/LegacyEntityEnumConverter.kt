package com.adevspoon.domain.config.converter

import com.adevspoon.domain.common.entity.LegacyEntityEnum
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * 각 Enum에서 Converter 구현하기
 * -
 */

@Converter
class LegacyEntityEnumConverter<T>(
    private var enumType: Class<T>,
    private var nullable: Boolean = false
) : AttributeConverter<T?, String?> where T: Enum<T>, T:LegacyEntityEnum {
    override fun convertToDatabaseColumn(attribute: T?): String? {
        if (!nullable && attribute == null) {
            throw IllegalArgumentException("Cannot convert null value to database column when nullable is false")
        }
        TODO("Not yet implemented")
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        TODO("Not yet implemented")
    }
}