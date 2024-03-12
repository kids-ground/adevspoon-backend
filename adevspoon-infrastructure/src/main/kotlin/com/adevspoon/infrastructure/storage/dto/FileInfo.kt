package com.adevspoon.infrastructure.storage.dto

import java.io.InputStream

data class FileInfo(
    val file: InputStream,
    val size: Int,
    val extension: FileExtension,
    val rootPath: String?,
)

enum class FileExtension(
    val value: String
){
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    HEIC("heic");
}