package com.icdid.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.auth.domain.NoteMarkAuthRepository
import com.icdid.auth.domain.UserDataValidator
import com.icdid.auth.presentation.R
import com.icdid.core.domain.DataError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.icdid.core.domain.Result
import com.icdid.core.presentation.utils.UiText
import com.icdid.core.presentation.utils.asUiText

class LoginViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: NoteMarkAuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _event = Channel<LoginEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginClicked -> onLoginClicked()
            is LoginAction.OnEmailChanged -> onEmailChanged(action.email)
            is LoginAction.OnPasswordChanged -> onPasswordChanged(action.password)
            else -> Unit
        }
    }

    private fun onLoginClicked() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    loading = true,
                    isLoginButtonEnabled = false,
                )
            }
            val result = authRepository.login(
                email = _state.value.email,
                password = _state.value.password,
            )
            _state.update { currentState ->
                currentState.copy(
                    loading = false,
                    isLoginButtonEnabled = true
                )
            }

            when(result) {
                is Result.Error -> {
                    if(result.error == DataError.Network.UNAUTHORIZED) {
                        _event.send(
                            LoginEvent.Error(UiText.StringResource(R.string.invalid_login_credentials))
                        )
                    } else {
                        _event.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> {
                    _event.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }

    private fun onEmailChanged(email: String) {
        _state.update { currentState ->
            currentState.copy(
                email = email,
                isLoginButtonEnabled = userDataValidator.isValidEmail(email)
                        && currentState.password.isNotEmpty()
            )
        }
    }

    private fun onPasswordChanged(password: String) {
        _state.update { currentState ->
            currentState.copy(
                password = password,
                isLoginButtonEnabled = userDataValidator.isValidEmail(currentState.email)
                        && password.isNotEmpty()
            )
        }
    }
}