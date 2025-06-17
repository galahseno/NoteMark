package com.icdid.dashboard.presentation.all_notes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.core.presentation.composables.NoteMarkDefaultScreen
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllNotesRoot(
    viewModel: AllNotesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->

    }

    AllNotesScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun AllNotesScreen(
    state: AllNotesState,
    onAction: (AllNotesAction) -> Unit,
) {
    NoteMarkDefaultScreen {
        Text(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 16.dp,
                ),
            text = "All notes",
            style = LocalNoteMarkTypography.current.titleXLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        AllNotesScreen(
            state = AllNotesState(),
            onAction = {}
        )
    }
}