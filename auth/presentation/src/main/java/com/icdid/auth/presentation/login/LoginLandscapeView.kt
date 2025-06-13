package com.icdid.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.login.composables.LoginFormView
import com.icdid.auth.presentation.login.composables.LoginTitleView

@Composable
fun LoginLandscapeView(
    modifier: Modifier = Modifier,
    email: String = "",
    password: String = "",
    isLoginButtonEnabled: Boolean = false,
    isLoading: Boolean = false,
    onEmailTextChange: (String) -> Unit = {},
    onPasswordTextChange: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(
                top = 32.dp,
                start = 60.dp,
                end = 40.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        LoginTitleView(
            modifier = Modifier
                .weight(1f)
        )
        LoginFormView(
            modifier = Modifier
                .weight(1f),
            email = email,
            password = password,
            isLoginButtonEnabled = isLoginButtonEnabled,
            isLoading = isLoading,
            onEmailTextChanged = { onEmailTextChange(it) },
            onPasswordTextChanged = { onPasswordTextChange(it) }
        )
    }
}

@Composable
@Preview
fun LoginLandscapeViewPreview() {
    LoginLandscapeView()
}