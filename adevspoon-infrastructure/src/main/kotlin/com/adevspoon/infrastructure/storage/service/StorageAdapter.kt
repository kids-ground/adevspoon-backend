package com.adevspoon.infrastructure.storage.service

import com.adevspoon.infrastructure.storage.dto.FileInfo

interface StorageAdapter {
    fun uploadImage(file: FileInfo): String
    fun uploadImageList(files: List<FileInfo>): List<String>
}