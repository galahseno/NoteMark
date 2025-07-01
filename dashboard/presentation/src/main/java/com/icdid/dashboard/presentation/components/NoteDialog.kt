package com.icdid.dashboard.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.dashboard.presentation.R

@Composable
fun NoteDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    content: String,
    confirmText: String,
    dismissText: String
) {
    if (showDialog) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = true,
                decorFitsSystemWindows = true
            ),
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    style = LocalNoteMarkTypography.current.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            },
            text = {
                Text(
                    text = content,
                    style = LocalNoteMarkTypography.current.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirm
                ) {
                    Text(
                        confirmText,
                        style = LocalNoteMarkTypography.current.bodyMedium
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(
                        dismissText,
                        style = LocalNoteMarkTypography.current.bodyMedium
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun NoteDialogPreview() {
    NoteMarkTheme {
        NoteDialog(
            showDialog = true,
            onDismiss = {},
            onConfirm = {},
            title = stringResource(R.string.delete_note),
            content = stringResource(R.string.delete_confirmation),
            confirmText = stringResource(R.string.delete),
            dismissText = stringResource(R.string.cancel)
        )
    }
}