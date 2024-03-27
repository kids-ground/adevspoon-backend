package com.adevspoon.domain.common.converter

import com.adevspoon.domain.common.entity.LegacyEntityEnum
import com.adevspoon.domain.common.exception.DomainInvalidAttributeException
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * 각 Enum에서 Converter 구현하기
 * -
 */


@Converter
abstract class LegacyEntityEnumConverter<T>(
    private var enumType: Class<T>,
    private var nullable: Boolean = false
) : AttributeConverter<T?, String?> where T: Enum<T>, T:LegacyEntityEnum {
    override fun convertToDatabaseColumn(attribute: T?): String? {
        if (!nullable && attribute == null) {
            throw DomainInvalidAttributeException(type = enumType.simpleName)
        }

        return attribute?.name?.lowercase()
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        if (!nullable && dbData == null) {
            throw DomainInvalidAttributeException(type = enumType.simpleName)
        } else if (nullable && dbData == null) {
            return null
        }

        val data = dbData?.uppercase()
        return enumType.enumConstants
            .firstOrNull { it.name == data }
            ?: throw DomainInvalidAttributeException(type = enumType.simpleName)
    }
}