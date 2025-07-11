package com.icdid.dashboard.presentation.note_detail.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePreviews
import com.icdid.core.presentation.utils.TabletPreviews
import com.icdid.core.presentation.utils.UiText
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.note_detail.NoteDetailState

@Composable
fun NoteViewMode(
    modifier: Modifier = Modifier,
    state: NoteDetailState,
    isTablet: Boolean,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = state.title,
            style = if (isTablet) LocalNoteMarkTypography.current.titleXLarge
                else LocalNoteMarkTypography.current.titleLarge,
            modifier = Modifier
                .padding(start = 16.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(bottom = 4.dp),
            color = MaterialTheme.colorScheme.surface,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = if (isTablet) 24.dp else 16.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.date_created),
                        style = LocalNoteMarkTypography.current.bodySmall
                    )
                    Text(
                        text = state.createdAt?.asString() ?: "",
                        style = LocalNoteMarkTypography.current.titleSmall
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.last_edited),
                        style = LocalNoteMarkTypography.current.bodySmall
                    )
                    Text(
                        text = state.lastEditedAt?.asString() ?: "",
                        style = LocalNoteMarkTypography.current.titleSmall
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 4.dp),
            color = MaterialTheme.colorScheme.surface,
        )

        Text(
            text = state.content,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = LocalNoteMarkTypography.current.bodyLarge
        )
    }
}

@MobilePreviews
@TabletPreviews
@Composable
private fun NoteViewModePreview() {
    NoteMarkTheme {
        NoteViewMode(
            state = NoteDetailState(
                title = "Note Title",
                createdAt = UiText.DynamicString("2021-01-01"),
                lastEditedAt = UiText.DynamicString("2021-01-01"),
                content = "Note Content"
            ),
            isTablet = false
        )
    }
}