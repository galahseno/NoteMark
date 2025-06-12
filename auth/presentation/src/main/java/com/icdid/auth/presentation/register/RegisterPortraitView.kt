package com.icdid.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.register.composables.RegisterFormView
import com.icdid.auth.presentation.register.composables.RegisterTitleView
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePortrait
import com.icdid.core.presentation.utils.TabletPortrait

@Composable
fun RegisterPortraitView(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier,
    isTablet: Boolean = false,
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
        horizontalAlignment = if(isTablet) Alignment.CenterHorizontally else Alignment.Start,
    ) {
        RegisterTitleView(isTablet = isTablet)
        RegisterFormView(
            state = state,
            onAction = onAction,
        )
    }
}

@MobilePortrait
@Composable
private fun RegisterPortraitViewPreview() {
    NoteMarkTheme {
        RegisterPortraitView(
            state = RegisterState(),
            onAction = {}
        )
    }
}

@TabletPortrait
@Composable
private fun RegisterPortraitViewTabletPreview() {
    NoteMarkTheme {
        RegisterPortraitView(
            state = RegisterState(),
            onAction = {},
            isTablet = true
        )
    }
}