package com.adevspoon.common.enums

enum class FileExtension(
    val value: String
){
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    HEIC("heic");

    companion object {
        fun fromValue(value: String): FileExtension? = values().find { it.value == value }
    }
}