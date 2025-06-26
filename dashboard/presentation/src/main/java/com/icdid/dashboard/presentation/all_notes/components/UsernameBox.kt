package com.icdid.dashboard.presentation.all_notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.R

@Composable
fun UsernameBox(
    username: String,
    modifier: Modifier = Modifier
) {
    val userInitialsText = stringResource(R.string.user_initials)
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clearAndSetSemantics {
                this.contentDescription = userInitialsText + username
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            username,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UsernameBoxPreview() {
    NoteMarkTheme {
        UsernameBox(
            username = "PQ",
        )
    }
}