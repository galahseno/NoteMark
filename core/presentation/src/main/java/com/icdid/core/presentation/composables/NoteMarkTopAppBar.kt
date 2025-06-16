package com.icdid.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.R
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteMarkTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkTopAppBarPreview() {
    NoteMarkTheme {
        NoteMarkTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.notemark),
                    style = LocalNoteMarkTypography.current.titleMedium
                )
            },
            actions = {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "PL",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkTopAppBarPreviewDetail() {
    NoteMarkTheme {
        NoteMarkTopAppBar(
            title = {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            actions = {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = stringResource(R.string.save_note).uppercase(),
                    style = LocalNoteMarkTypography.current.titleSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        )
    }
}