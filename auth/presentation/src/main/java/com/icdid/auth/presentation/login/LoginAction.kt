package com.icdid.auth.presentation.login

sealed interface LoginAction {
    data object OnLoginClicked: LoginAction
    data class OnEmailChanged(val email: String): LoginAction
    data class OnPasswordChanged(val password: String): LoginAction
    data object OnRegisterClicked: LoginAction
}