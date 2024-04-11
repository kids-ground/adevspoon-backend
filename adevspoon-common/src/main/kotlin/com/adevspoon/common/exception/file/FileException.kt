package com.adevspoon.common.exception.file

import com.adevspoon.common.exception.AdevspoonException
import com.adevspoon.common.exception.ExternalException

class FileNameEmptyException : AdevspoonException(FileErrorCode.FILE_NAME_EMPTY)
class FileExtensionEmptyException : AdevspoonException(FileErrorCode.FILE_EXTENSION_EMPTY)
class FileExtensionNotSupportedException : AdevspoonException(FileErrorCode.FILE_EXTENSION_NOT_SUPPORT)
class FileResizeImageFailedException(reason: String?) : ExternalException(FileErrorCode.RESIZE_IMAGE_FAILED, reason)