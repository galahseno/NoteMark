package com.icdid.auth.presentation.register

sealed interface RegisterAction {
    data class OnUsernameChanged(val username: String) : RegisterAction
    data class OnEmailChanged(val email: String): RegisterAction
    data class OnPasswordChanged(val password: String): RegisterAction
    data class OnRepeatPasswordChanged(val repeatPassword: String): RegisterAction
    object OnLoginClicked: RegisterAction
    object OnRegisterClicked: RegisterAction
}