package com.icdid.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme

@Composable
fun NoteMarkPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    val backgroundColor = if(enabled) color else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.medium
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        if(isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = text,
                style = LocalNoteMarkTypography.current.titleSmall.copy(
                    color = if(enabled) textColor else Color(0xFF979797),
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkPrimaryButtonComponent() {
    NoteMarkTheme {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            NoteMarkPrimaryButton(
                text = "Test",
                onClick = {},
            )
            NoteMarkPrimaryButton(
                text = "Test",
                onClick = {},
                enabled = false,
                isLoading = true
            )
        }
    }
}