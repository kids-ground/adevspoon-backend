package com.adevspoon.infrastructure.storage.dto

import com.adevspoon.common.enums.FileExtension
import java.io.InputStream

data class FileInfo(
    val file: InputStream,
    val size: Int,
    val extension: FileExtension,
    val rootPath: String? = null,
)