package com.icdid.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.register.composables.RegisterFormView
import com.icdid.auth.presentation.register.composables.RegisterTitleView
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobileLandscape
import com.icdid.core.presentation.utils.TabletLandscape

@Composable
fun RegisterLandscapeView(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier,
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
        RegisterTitleView(
            modifier = Modifier
                .weight(1f)
        )
        RegisterFormView(
            modifier = Modifier
                .weight(1f),
            state = state,
            onAction = onAction
        )
    }
}

@MobileLandscape
@TabletLandscape
@Composable
private fun RegisterLandscapeViewPreview() {
    NoteMarkTheme {
        RegisterLandscapeView(
            state = RegisterState(),
            onAction = {}
        )
    }
}