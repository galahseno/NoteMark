package com.icdid.auth.presentation.login.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.LocalNoteMarkTypography

@Composable
fun LoginTitleView(
    modifier: Modifier = Modifier,
    isTablet: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if(isTablet) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Log In",
            style = LocalNoteMarkTypography.current.titleXLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = if(isTablet) TextAlign.Center else TextAlign.Start,
            )
        )

        Text(
            text = "Capture your thoughts and ideas",
            style = LocalNoteMarkTypography.current.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )

        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Preview
@Composable
private fun LoginTitlePreview() {
    LoginTitleView()
}