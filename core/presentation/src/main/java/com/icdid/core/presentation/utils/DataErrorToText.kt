package com.icdid.core.presentation.utils

import com.icdid.core.domain.DataError
import com.icdid.core.presentation.R

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )

        DataError.Network.TOO_MANY_REQUEST -> UiText.StringResource(
            R.string.error_too_many_request

        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )

        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        else -> UiText.StringResource(
            R.string.error_unknown
        )
    }
}