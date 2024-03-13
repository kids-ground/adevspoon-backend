package com.adevspoon.infrastructure.storage.util

import java.util.*

fun getImageRootPath() = UUID.randomUUID().toString()

fun getImageUploadPath(rootPath: String, size: Int, extension: String) =
    "uploads/$rootPath/img_${size}x${size}.$extension"