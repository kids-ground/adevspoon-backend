package com.adevspoon.api.config.controller.converter

import com.adevspoon.api.common.dto.LegacyDtoEnum
import com.adevspoon.api.common.exception.ApiInvalidEnumException
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory

class StringToLegacyDtoEnumConverterFactory<F>: ConverterFactory<String, F> where F: Enum<*>, F: LegacyDtoEnum {
    override fun <T : F> getConverter(targetType: Class<T>): Converter<String, T> {
        return StringToEnumConverter(targetType)
    }

    @Suppress("UNCHECKED_CAST")
    private class StringToEnumConverter<T : Enum<*>?>(private val enumType: Class<T>) : Converter<String, T> {
        override fun convert(source: String): T {
            val value = source.uppercase()
            val enumValue = (enumType as Class<out Enum<*>>)
                .enumConstants
                .firstOrNull { it.name == value }
                ?: throw ApiInvalidEnumException()
            return enumValue as T
        }
    }
}