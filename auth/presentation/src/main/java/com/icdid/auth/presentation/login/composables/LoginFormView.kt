package com.icdid.auth.presentation.login.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.R
import com.icdid.auth.presentation.login.LoginAction
import com.icdid.auth.presentation.login.LoginState
import com.icdid.core.presentation.composables.NoteMarkDefaultTextButton
import com.icdid.core.presentation.composables.NoteMarkPrimaryButton
import com.icdid.core.presentation.composables.NoteMarkTextField
import com.icdid.core.presentation.theme.NoteMarkTheme

@Composable
fun LoginFormView(
    modifier: Modifier = Modifier,
    state: LoginState = LoginState(),
    onAction: (LoginAction) -> Unit = {}
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        NoteMarkTextField(
            value = state.email,
            label = stringResource(R.string.email),
            placeholder = "john.doe@example.com",
            onValueChange = { onAction(LoginAction.OnEmailChanged(it)) }
        )

        Spacer(modifier = Modifier.size(16.dp))

        NoteMarkTextField(
            value = state.password,
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            onValueChange = { onAction(LoginAction.OnPasswordChanged(it)) },
            isPassword = true,
        )

        Spacer(modifier = Modifier.size(24.dp))

        NoteMarkPrimaryButton(
            text = stringResource(R.string.login_text),
            onClick = { onAction(LoginAction.OnLoginClicked) },
            enabled = state.isLoginButtonEnabled,
            isLoading = state.loading,
        )

        Spacer(modifier = Modifier.size(24.dp))

        NoteMarkDefaultTextButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onAction(LoginAction.OnRegisterClicked) },
            text = stringResource(R.string.have_not_account),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginFormPreview() {
    NoteMarkTheme {
        LoginFormView()
    }
}
