package com.icdid.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.login.composables.LoginFormView
import com.icdid.auth.presentation.login.composables.LoginTitleView
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobileLandscape
import com.icdid.core.presentation.utils.TabletLandscape

@Composable
fun LoginLandscapeView(
    modifier: Modifier = Modifier,
    state: LoginState = LoginState(),
    onAction: (LoginAction) -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(
                top = 32.dp,
                start = 60.dp,
                end = 40.dp,
            )
            .imePadding(),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        LoginTitleView(
            modifier = Modifier
                .weight(1f),
        )
        LoginFormView(
            modifier = Modifier
                .weight(1f),
            state = state,
            onAction = onAction,
        )
    }
}

@MobileLandscape
@TabletLandscape
@Composable
private fun LoginLandscapeViewPreview() {
    NoteMarkTheme {
        LoginLandscapeView()
    }
}