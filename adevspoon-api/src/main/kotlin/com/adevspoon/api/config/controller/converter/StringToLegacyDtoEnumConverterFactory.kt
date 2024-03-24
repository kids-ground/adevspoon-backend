package com.adevspoon.api.config.controller.converter

import com.adevspoon.api.common.dto.LegacyDtoEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory

class StringToLegacyDtoEnumConverterFactory<F>: ConverterFactory<String, F> where F: Enum<*>, F: LegacyDtoEnum {
    override fun <T : F> getConverter(targetType: Class<T>): Converter<String, T> {
        return StringToEnumConverter(targetType)
    }

    @Suppress("UNCHECKED_CAST")
    private class StringToEnumConverter<T : Enum<*>?>(private val enumType: Class<T>) : Converter<String, T> {
        override fun convert(source: String): T {
            val type = enumType as Class<out Enum<*>>
            return java.lang.Enum.valueOf(type, source.uppercase()) as T
        }
    }
}