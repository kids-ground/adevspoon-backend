package com.adevspoon.common.enums

import com.fasterxml.jackson.annotation.JsonCreator

enum class SocialType {
    KAKAO,
    APPLE;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromString(value: String): SocialType {
            return valueOf(value.uppercase())
        }
    }
}