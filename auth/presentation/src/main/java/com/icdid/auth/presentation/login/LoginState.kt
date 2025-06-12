package com.icdid.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
) {
    val isLoginButtonEnabled = email.isNotEmpty() && password.isNotEmpty()
}