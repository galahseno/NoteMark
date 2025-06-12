package com.icdid.auth.presentation.register

import com.icdid.auth.domain.model.PasswordValidationState
import com.icdid.auth.domain.model.UsernameValidationState

data class RegisterState(
    val username: String = "",
    val usernameValidation: UsernameValidationState = UsernameValidationState(),
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val passwordValidation: PasswordValidationState = PasswordValidationState(),
    val repeatPassword: String = "",
    val isRepeatPasswordValid: Boolean = false,
    val canRegister: Boolean = false,
    val isLoading: Boolean = false,
)