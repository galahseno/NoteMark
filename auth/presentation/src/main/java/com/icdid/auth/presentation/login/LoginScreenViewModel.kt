package com.icdid.auth.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginButtonClicked -> onLoginButtonClicked()
            is LoginAction.OnEmailTextChange -> onEmailTextChange(action.email)
            is LoginAction.OnPasswordTextChange -> onPasswordTextChange(action.password)
            else -> Unit
        }
    }

    private fun onLoginButtonClicked() {
        _state.update { currentState ->
            currentState.copy(
                loading = true
            )
        }
    }

    private fun onEmailTextChange(email: String) {
        _state.update { currentState ->
            currentState.copy(
                email = email,
            )
        }
    }

    private fun onPasswordTextChange(password: String) {
        _state.update { currentState ->
            currentState.copy(
                password = password,
            )
        }
    }
}