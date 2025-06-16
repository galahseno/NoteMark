package com.icdid.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.dashboard.presentation.all_notes.AllNotesAction
import com.icdid.dashboard.presentation.model.NotesContentType
import com.icdid.dashboard.presentation.util.toContentPreview
import com.icdid.dashboard.presentation.util.toDisplayDate

@Composable
fun NoteContent(
    date: String,
    title: String,
    content: String,
    onAction: (AllNotesAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        onAction(AllNotesAction.OnLongNoteDisplayDialog)
                    },
                )
            }
            .clickable {
                // TODO navigate to detail
            }
            .padding(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = date,
                style = LocalNoteMarkTypography.current.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = title,
                style = LocalNoteMarkTypography.current.titleMedium.copy(
                )
            )
            Text(
                text = content,
                style = LocalNoteMarkTypography.current.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Preview
@Composable
private fun NoteContentPreview() {
    NoteMarkTheme {
        NoteContent(
            date = "2024-04-19T08:30:00.000Z".toDisplayDate(),
            title = "Sample Note",
            content = "This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content. This is a sample note content."
                .toContentPreview(NotesContentType.TABLET),
            onAction = {

            }
        )
    }
}