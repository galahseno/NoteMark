package com.icdid.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme

@Composable
fun NoteMarkOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    borderColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.medium
            )
            .padding(vertical = 12.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            style = LocalNoteMarkTypography.current.titleSmall.copy(
                color = textColor,
                textAlign = TextAlign.Center,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkOutlinedButtonPreview() {
    NoteMarkTheme {
        NoteMarkOutlinedButton(
            text = "Test",
            onClick = {}
        )
    }
}