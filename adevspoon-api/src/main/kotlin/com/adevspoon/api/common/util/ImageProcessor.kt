package com.adevspoon.api.common.util

import com.adevspoon.api.common.properties.ImageProperties
import com.adevspoon.common.enums.FileExtension
import com.adevspoon.common.enums.ImageType
import com.adevspoon.common.exception.FileErrorCode
import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*

@Component
class ImageProcessor(
    private val imageProperties: ImageProperties
) {
    fun getExtension(fileName: String?): FileExtension {
        if (fileName == null) throw FileErrorCode.FILE_NAME_EMPTY.getException()
        val extension = StringUtils.getFilenameExtension(fileName) ?: throw FileErrorCode.FILE_EXTENSION_EMPTY.getException()

        return FileExtension
            .fromValue(extension)
            ?: throw FileErrorCode.FILE_EXTENSION_NOT_SUPPORT.getException()
    }

    fun resize(file: InputStream, type: ImageType, extension: FileExtension): InputStream {
        val tempFile = File("${imageProperties.tempDir}/${UUID.randomUUID()}.${extension.value}")
        try {
            Thumbnails.of(file)
                .size(type.size, type.size)
                .toFile(tempFile)

            return FileInputStream(tempFile)
        } catch (e: Exception) {
            throw FileErrorCode.RESIZE_IMAGE_FAILED.getExternalException(e.message ?: "썸네일 생성 오류 발생")
        } finally {
            tempFile.delete()
        }
    }
}