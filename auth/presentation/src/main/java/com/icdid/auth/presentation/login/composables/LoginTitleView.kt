package com.icdid.auth.presentation.login.composables

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.R
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme

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
            text = stringResource(R.string.login_text),
            style = LocalNoteMarkTypography.current.titleXLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = if(isTablet) TextAlign.Center else TextAlign.Start,
            )
        )

        Text(
            text = stringResource(R.string.auth_subtitle),
            style = LocalNoteMarkTypography.current.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )

        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginTitlePreview() {
    NoteMarkTheme {
        LoginTitleView()
    }
}