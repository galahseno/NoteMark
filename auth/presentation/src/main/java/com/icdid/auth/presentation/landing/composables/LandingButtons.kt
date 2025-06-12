package com.icdid.auth.presentation.landing.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.auth.presentation.R
import com.icdid.auth.presentation.landing.LandingAction
import com.icdid.core.presentation.composables.NoteMarkOutlinedButton
import com.icdid.core.presentation.composables.NoteMarkPrimaryButton
import com.icdid.core.presentation.theme.NoteMarkTheme

@Composable
fun LandingButtons(
    modifier: Modifier = Modifier,
    onAction: (LandingAction) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NoteMarkPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
            text = stringResource(R.string.landing_button_text),
            onClick = {
                onAction(LandingAction.OnGettingStartedClicked)
            },
            color = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            enabled = true
        )

        NoteMarkOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
            text = stringResource(R.string.login_text),
            onClick = {
                onAction(LandingAction.OnLoginClicked)
            },
            borderColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LandingButtonPreview() {
    NoteMarkTheme {
        LandingButtons(
            onAction = {}
        )
    }
}