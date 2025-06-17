package com.icdid.auth.presentation.login

import com.icdid.core.presentation.utils.UiText

sealed interface LoginEvent {
    data object LoginSuccess: LoginEvent
    data class Error(val error: UiText): LoginEvent
}