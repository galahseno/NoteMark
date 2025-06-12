package com.icdid.auth.domain

import com.icdid.auth.domain.model.PasswordValidationState
import com.icdid.auth.domain.model.UsernameValidationState

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun validateUsername(username: String): UsernameValidationState {
        val hasMinLength = username.length >= MIN_USERNAME_LENGTH
        val isWithinMaxLength = username.length <= MAX_USERNAME_LENGTH

        return UsernameValidationState(
            hasMinLength = hasMinLength,
            isWithinMaxLength = isWithinMaxLength
        )
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasNumberOrSymbol = password.any { it.isDigit() || !it.isLetterOrDigit() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumberOrSymbol = hasNumberOrSymbol
        )
    }

    companion object {
        const val MIN_USERNAME_LENGTH = 3
        const val MAX_USERNAME_LENGTH = 20
        const val MIN_PASSWORD_LENGTH = 8
    }
}