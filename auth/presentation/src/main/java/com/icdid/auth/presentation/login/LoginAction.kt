package com.icdid.auth.presentation.login

sealed interface LoginAction {
    data object OnLoginButtonClicked: LoginAction
    data class OnEmailTextChange(val email: String): LoginAction
    data class OnPasswordTextChange(val password: String): LoginAction
}