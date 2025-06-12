package com.icdid.auth.domain.model

data class UsernameValidationState(
    val hasMinLength: Boolean = false,
    val isWithinMaxLength: Boolean = false
) {
    val isValidUsername: Boolean
        get() = hasMinLength && isWithinMaxLength
}