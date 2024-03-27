package com.adevspoon.domain.common.converter

import com.adevspoon.domain.common.exception.DomainInvalidAttributeException
import com.adevspoon.domain.member.domain.enums.AuthRole
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class AuthRoleToStringConverter: AttributeConverter<AuthRole, String> {
    override fun convertToDatabaseColumn(attribute: AuthRole): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): AuthRole {
        return AuthRole.values()
            .firstOrNull { it.value == dbData }
            ?: throw DomainInvalidAttributeException("AuthRole")
    }
}