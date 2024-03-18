package com.adevspoon.api.common.util

import com.adevspoon.api.common.properties.ImageProperties
import com.adevspoon.common.exception.FileExtensionEmptyException
import com.adevspoon.common.exception.FileExtensionNotSupportedException
import com.adevspoon.common.exception.FileNameEmptyException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageProcessorTest {

    lateinit var imageProcessor: ImageProcessor

    @BeforeAll
    fun setUp() {
        imageProcessor = ImageProcessor(ImageProperties("/tmp"))
    }

    @Nested
    inner class GetExtension {
        @Test
        fun `FAIL - 파일이름이 없음`() {
            val fileNameEmpty = ""
            val fileNameNull: String? = null

            assertThrows(FileNameEmptyException::class.java) {
                imageProcessor.getExtension(fileNameEmpty)
            }
            assertThrows(FileNameEmptyException::class.java) {
                imageProcessor.getExtension(fileNameNull)
            }
        }

        @Test
        fun `FAIL - 파일 확장자가 없음`() {
            val fileName = "testFile"
            assertThrows(FileExtensionEmptyException::class.java) {
                imageProcessor.getExtension(fileName)
            }
        }

        @Test
        fun `FAIL - 지원하지 않는 확장자`() {
            val fileName = "testFile.mp4"
            assertThrows(FileExtensionNotSupportedException::class.java) {
                imageProcessor.getExtension(fileName)
            }
        }

        @Test
        fun `SUCCESS`() {
            val fileName = "testFile.jpg"
            imageProcessor.getExtension(fileName)
        }
    }

    @Test
    fun resize() {
    }
}