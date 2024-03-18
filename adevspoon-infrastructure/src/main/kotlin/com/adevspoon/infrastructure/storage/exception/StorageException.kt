package com.adevspoon.infrastructure.storage.exception

import com.adevspoon.common.exception.AdevspoonException
import com.adevspoon.common.exception.ExternalException

class StorageS3UploadException(reason: String?) : ExternalException(StorageErrorCode.S3_UPLOAD_ERROR, reason)