package com.icdid.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.login.composables.LoginFormView
import com.icdid.auth.presentation.login.composables.LoginTitleView
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePortrait
import com.icdid.core.presentation.utils.TabletPortrait

@Composable
fun LoginPortraitView(
    modifier: Modifier = Modifier,
    isTablet: Boolean = false,
    state: LoginState = LoginState(),
    onAction: (LoginAction) -> Unit = {}
) {
    val customModifier = if (isTablet) {
        modifier.padding(
            top = 100.dp,
            start = 120.dp,
            end = 120.dp,
        )
    } else {
        modifier.padding(
            top = 32.dp,
            start = 16.dp,
            end = 16.dp,
        )
    }

    Column(
        modifier = customModifier,
        horizontalAlignment = if (isTablet) Alignment.CenterHorizontally else Alignment.Start,
    ) {
        LoginTitleView(isTablet = isTablet)
        LoginFormView(
            state = state,
            onAction = onAction
        )
    }
}

@MobilePortrait
@Composable
private fun LoginPortraitViewPreview() {
    NoteMarkTheme {
        LoginPortraitView()
    }
}

@TabletPortrait
@Composable
private fun LoginPortraitTabletPreview() {
    NoteMarkTheme {
        LoginPortraitView(
            isTablet = true
        )
    }
}