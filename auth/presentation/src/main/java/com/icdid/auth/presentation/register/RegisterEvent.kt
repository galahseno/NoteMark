package com.icdid.auth.presentation.register

import com.icdid.core.presentation.utils.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}