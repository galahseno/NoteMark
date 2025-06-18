package com.icdid.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdid.auth.domain.AuthRepository
import com.icdid.auth.domain.UserDataValidator
import com.icdid.auth.presentation.R
import com.icdid.core.domain.DataError
import com.icdid.core.domain.Result
import com.icdid.core.presentation.utils.UiText
import com.icdid.core.presentation.utils.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _event = Channel<RegisterEvent>()
    val event = _event.receiveAsFlow()

    init {
        combine(
            _state.map { it.username }.distinctUntilChanged(),
            _state.map { it.email }.distinctUntilChanged(),
            _state.map { it.password }.distinctUntilChanged(),
            _state.map { it.repeatPassword }.distinctUntilChanged(),
            _state.map { it.isLoading }.distinctUntilChanged()
        ) { username, email, password, repeatPassword, isLoading ->
            validateAllFields(username, email, password, repeatPassword, isLoading)
        }.launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnUsernameChanged -> {
                _state.update { it.copy(username = action.username) }
            }

            is RegisterAction.OnEmailChanged -> {
                _state.update { it.copy(email = action.email) }
            }

            is RegisterAction.OnPasswordChanged -> {
                _state.update { it.copy(password = action.password) }
            }

            is RegisterAction.OnRepeatPasswordChanged -> {
                _state.update { it.copy(repeatPassword = action.repeatPassword) }
            }

            RegisterAction.OnRegisterClicked -> register()

            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = authRepository.register(
                username = _state.value.username,
                email = _state.value.email.trim(),
                password = _state.value.password
            )
            _state.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        _event.send(
                            RegisterEvent.Error(
                                UiText.StringResource(R.string.error_user_exist)
                            )
                        )
                    } else {
                        _event.send(RegisterEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _event.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }

    private fun validateAllFields(
        username: String,
        email: String,
        password: String,
        repeatPassword: String,
        isLoading: Boolean
    ) {
        val usernameValidation = userDataValidator.validateUsername(username)
        val isEmailValid = userDataValidator.isValidEmail(email)
        val passwordValidation = userDataValidator.validatePassword(password)
        val isRepeatPasswordValid = password == repeatPassword

        _state.update {
            it.copy(
                usernameValidation = usernameValidation,
                isEmailValid = isEmailValid,
                passwordValidation = passwordValidation,
                isRepeatPasswordValid = isRepeatPasswordValid,
                canRegister = usernameValidation.isValidUsername &&
                        isEmailValid &&
                        passwordValidation.isValidPassword &&
                        isRepeatPasswordValid && !isLoading
            )
        }
    }
}