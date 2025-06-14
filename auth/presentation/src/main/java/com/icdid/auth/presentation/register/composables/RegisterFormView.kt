package com.icdid.auth.presentation.register.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.R
import com.icdid.auth.presentation.register.RegisterAction
import com.icdid.auth.presentation.register.RegisterState
import com.icdid.core.presentation.composables.NoteMarkPrimaryButton
import com.icdid.core.presentation.composables.NoteMarkTextField
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme

@Composable
fun RegisterFormView(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        NoteMarkTextField(
            value = state.username,
            label = stringResource(R.string.username),
            placeholder = "John.doe",
            supportingText = stringResource(R.string.username_support_text),
            errorMessage = when {
                !state.usernameValidation.hasMinLength && state.username.isNotEmpty() ->
                    stringResource(R.string.username_error_min_length)

                !state.usernameValidation.isWithinMaxLength && state.username.isNotEmpty() ->
                    stringResource(R.string.username_error_max_length)

                else -> null
            },
            onValueChange = { onAction(RegisterAction.OnUsernameChanged(it)) }
        )

        Spacer(modifier = Modifier.size(16.dp))

        NoteMarkTextField(
            value = state.email,
            label = stringResource(R.string.email),
            placeholder = "john.doe@example.com",
            errorMessage = if (!state.isEmailValid && state.email.isNotEmpty())
                stringResource(R.string.email_invalid) else null,
            onValueChange = { onAction(RegisterAction.OnEmailChanged(it)) }
        )

        Spacer(modifier = Modifier.size(16.dp))

        NoteMarkTextField(
            value = state.password,
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            supportingText = stringResource(R.string.password_support_text),
            errorMessage = if (!state.passwordValidation.isValidPassword && state.password.isNotEmpty())
                stringResource(R.string.password_invalid) else null,
            onValueChange = { onAction(RegisterAction.OnPasswordChanged(it)) },
            isPassword = true,
        )

        Spacer(modifier = Modifier.size(16.dp))

        NoteMarkTextField(
            value = state.repeatPassword,
            label = stringResource(R.string.repeat_password),
            placeholder = stringResource(R.string.password),
            errorMessage = if (!state.isRepeatPasswordValid && state.repeatPassword.isNotEmpty())
                stringResource(R.string.repeat_password_invalid) else null,
            onValueChange = { onAction(RegisterAction.OnRepeatPasswordChanged(it)) },
            isPassword = true,
        )

        Spacer(modifier = Modifier.size(24.dp))

        NoteMarkPrimaryButton(
            text = stringResource(R.string.create_account),
            onClick = { onAction(RegisterAction.OnRegisterClicked) },
            enabled = state.canRegister,
            isLoading = state.isLoading,
        )

        Spacer(modifier = Modifier.size(24.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAction(RegisterAction.OnLoginClicked) },
            text = stringResource(R.string.already_have_an_account),
            style = LocalNoteMarkTypography.current.titleSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun EmailFormViewPreview() {
    NoteMarkTheme {
        RegisterFormView(
            state = RegisterState(
                isLoading = true
            ),
            onAction = {}
        )
    }
}