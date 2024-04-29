package com.adevspoon.domain.utility.exception

import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

private val domain = DomainType.ETC

enum class UtilityDomainErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    POPUP_NOT_SUPPORTED(domain code 400 no 0, "Popup 을 지원하지 않습니다"),
}