package com.adevspoon.api.config.controller.serializer

import com.adevspoon.api.common.dto.LegacyDtoEnum
import com.adevspoon.api.common.exception.ApiInvalidEnumException
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.ContextualSerializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer

// LegacyDtoEnum을 변환하는 Serializer/Deserializer
// - 요청Body의 경우 소문자 -> 대문자로 변경해서 받기
// - 응답Body의 경우 대문자 -> 소문자로 변경해서 내보내기
// - LegacyDtoEnum에 설정 적용

class LegacyDtoEnumCombinedSerializer {
    class LegacyDtoEnumDeserializer<T>(vc: Class<*>?): StdDeserializer<T>(vc), ContextualDeserializer where T : LegacyDtoEnum, T: Enum<*> {
        constructor() : this(null)

        // 해당 요청 필드에 값이 존재할때만 들어옴
        @Suppress("UNCHECKED_CAST")
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T {
            val jsonNode: JsonNode = p.codec.readTree(p)
            val value: String = jsonNode.asText().uppercase()
            val enumValue = (_valueClass as Class<out Enum<*>>)
                .enumConstants
                .firstOrNull { it.name == value }
                ?: throw ApiInvalidEnumException(p.currentName)

            return enumValue as T
        }

        override fun createContextual(ctxt: DeserializationContext, property: BeanProperty): JsonDeserializer<*> {
            return LegacyDtoEnumDeserializer(property.type.rawClass)
        }
    }

    class LegacyDtoEnumSerializer<T>(vc: Class<*>?): StdSerializer<T>(vc, true), ContextualSerializer where T : LegacyDtoEnum, T: Enum<*> {
        constructor() : this(null)

        override fun serialize(value: T, gen: JsonGenerator, provider: SerializerProvider) {
            gen.writeString(value.name.lowercase())
        }

        override fun createContextual(prov: SerializerProvider, property: BeanProperty): JsonSerializer<*> {
            return LegacyDtoEnumSerializer(property.type.rawClass)
        }
    }
}