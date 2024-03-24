package com.adevspoon.api.common.dto

import com.adevspoon.api.config.controller.serializer.LegacyDtoEnumCombinedSerializer
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonDeserialize(using = LegacyDtoEnumCombinedSerializer.LegacyDtoEnumDeserializer::class)
@JsonSerialize(using = LegacyDtoEnumCombinedSerializer.LegacyDtoEnumSerializer::class)
interface LegacyDtoEnum