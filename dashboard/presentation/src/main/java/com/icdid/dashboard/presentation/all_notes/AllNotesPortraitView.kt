package com.icdid.dashboard.presentation.all_notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePortrait
import com.icdid.core.presentation.utils.TabletPortrait
import com.icdid.dashboard.presentation.all_notes.components.EmptyNotes
import com.icdid.dashboard.presentation.all_notes.components.NoteContent
import com.icdid.dashboard.presentation.model.NotesContentType
import com.icdid.dashboard.presentation.util.PreviewUtil
import com.icdid.dashboard.presentation.util.toContentPreview
import com.icdid.dashboard.presentation.util.toDisplayDate

@Composable
fun AllNotesPortraitView(
    state: AllNotesState,
    onAction: (AllNotesAction) -> Unit,
    modifier: Modifier = Modifier,
    isTablet: Boolean = false
) {
    if (state.notes.isEmpty() && !state.isLoading) {
        EmptyNotes(
            modifier = modifier
        )
    } else {
        LazyVerticalStaggeredGrid(
            modifier = modifier,
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                state.notes,
                key = { it.id },
            ) {
                NoteContent(
                    id = it.id,
                    date = it.createdAt.toDisplayDate(),
                    title = it.title,
                    content = it.content.toContentPreview(
                        if (isTablet) NotesContentType.TABLET else NotesContentType.PHONE
                    ),
                    onAction = onAction,
                    modifier = Modifier
                        .animateItem()
                )
            }
        }
    }
}

@MobilePortrait
@Composable
private fun RegisterPortraitViewPreview() {
    NoteMarkTheme {
        AllNotesPortraitView(
            state = AllNotesState(
                notes = PreviewUtil.noteSamples
            ),
            onAction = {}
        )
    }
}

@TabletPortrait
@Composable
private fun RegisterPortraitViewTabletPreview() {
    NoteMarkTheme {
        AllNotesPortraitView(
            state = AllNotesState(
                notes = PreviewUtil.noteSamples
            ),
            onAction = {},
            isTablet = true
        )
    }
}