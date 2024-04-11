package com.adevspoon.api.common.util

import com.adevspoon.api.common.properties.ImageProperties
import com.adevspoon.common.enums.FileExtension
import com.adevspoon.common.enums.ImageType
import com.adevspoon.common.exception.file.FileExtensionEmptyException
import com.adevspoon.common.exception.file.FileExtensionNotSupportedException
import com.adevspoon.common.exception.file.FileNameEmptyException
import com.adevspoon.common.exception.file.FileResizeImageFailedException
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
        if (fileName.isNullOrEmpty()) throw FileNameEmptyException()
        val extension = StringUtils.getFilenameExtension(fileName) ?: throw FileExtensionEmptyException()

        return FileExtension
            .fromValue(extension)
            ?: throw FileExtensionNotSupportedException()
    }

    fun resize(file: InputStream, type: ImageType, extension: FileExtension): InputStream {
        val tempFile = File("${imageProperties.tempDir}/${UUID.randomUUID()}.${extension.value}")
        try {
            Thumbnails.of(file)
                .size(type.size, type.size)
                .toFile(tempFile)

            return FileInputStream(tempFile)
        } catch (e: Exception) {
            throw FileResizeImageFailedException(e.message)
        } finally {
            tempFile.delete()
        }
    }
}