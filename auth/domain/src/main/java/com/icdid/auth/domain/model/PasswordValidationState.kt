package com.icdid.auth.domain.model

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasNumberOrSymbol: Boolean = false
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasNumberOrSymbol
}