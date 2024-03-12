package com.adevspoon.api.common.util

import com.adevspoon.api.common.properties.ImageProperties
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
    fun getImageExtension(fileName: String): String? = StringUtils.getFilenameExtension(fileName)

    fun resizeImage(file: InputStream, type: ImageType, extension: String): InputStream {
        val tempFile = File("${imageProperties.tempDir}/${UUID.randomUUID()}.$extension")
        try {
            Thumbnails.of(file)
                .size(type.size, type.size)
                .toFile(tempFile)

            return FileInputStream(tempFile)
        } catch (e: Exception) {
            throw e
        } finally {
            tempFile.delete()
        }
    }
}

enum class ImageType(val size: Int) {
    ORIGIN(640),
    THUMBNAIL(250)
}