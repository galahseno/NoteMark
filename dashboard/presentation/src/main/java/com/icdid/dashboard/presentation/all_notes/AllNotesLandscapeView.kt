package com.icdid.dashboard.presentation.all_notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobileLandscape
import com.icdid.core.presentation.utils.TabletLandscape
import com.icdid.dashboard.presentation.all_notes.components.EmptyNotes
import com.icdid.dashboard.presentation.components.NoteContent
import com.icdid.dashboard.presentation.model.NotesContentType
import com.icdid.dashboard.presentation.util.PreviewUtil
import com.icdid.dashboard.presentation.util.toContentPreview
import com.icdid.dashboard.presentation.util.toDisplayDate

@Composable
fun AllNotesLandscapeView(
    state: AllNotesState,
    onAction: (AllNotesAction) -> Unit,
    modifier: Modifier = Modifier,
    isTablet: Boolean = false
) {
    if (state.notes.isEmpty()) {
        EmptyNotes(
            modifier = modifier
        )
    } else {
        LazyVerticalStaggeredGrid(
            modifier = modifier,
            columns = StaggeredGridCells.Fixed(3),
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                state.notes,
                key = { it.content },
            ) {
                NoteContent(
                    date = it.date.toDisplayDate(),
                    title = it.title,
                    content = it.content.toContentPreview(
                        if (isTablet) NotesContentType.TABLET else NotesContentType.PHONE
                    ),
                    onAction = onAction,
                    modifier = Modifier
                )
            }
        }
    }
}

@MobileLandscape
@TabletLandscape
@Composable
private fun AllNotesLandscapeViewPreview() {
    NoteMarkTheme {
        AllNotesLandscapeView(
            state = AllNotesState(
                notes = PreviewUtil.noteSamples
            ),
            onAction = {

            }
        )
    }
}