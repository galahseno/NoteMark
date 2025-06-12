package com.icdid.auth.presentation.login.composables

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
import com.icdid.core.presentation.composables.NoteMarkPrimaryButton
import com.icdid.core.presentation.composables.NoteMarkTextField
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme

@Composable
fun LoginFormView(
    modifier: Modifier = Modifier,
    email: String = "",
    password: String = "",
    isLoginButtonEnabled: Boolean = false,
    isLoading: Boolean = false,
    onEmailTextChanged: (String) -> Unit = {},
    onPasswordTextChanged: (String) -> Unit = {},
    onRegisterClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        NoteMarkTextField(
            value = email,
            label = stringResource(R.string.email),
            placeholder = "john.doe@example.com",
            onValueChange = { onEmailTextChanged(it) }
        )

        Spacer(modifier = Modifier.size(16.dp))

        NoteMarkTextField(
            value = password,
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            onValueChange = { onPasswordTextChanged(it) },
            isPassword = true,
        )

        Spacer(modifier = Modifier.size(24.dp))

        NoteMarkPrimaryButton(
            text = stringResource(R.string.login_text),
            onClick = {},
            enabled = isLoginButtonEnabled,
            isLoading = isLoading,
        )

        Spacer(modifier = Modifier.size(24.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onRegisterClicked() },
            text = stringResource(R.string.have_not_account),
            style = LocalNoteMarkTypography.current.titleSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
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
