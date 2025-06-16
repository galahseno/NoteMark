package com.icdid.dashboard.presentation.all_notes

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.core.presentation.composables.NoteMarkFAB
import com.icdid.core.presentation.composables.NoteMarkTopAppBar
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePreviews
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.TabletPreviews
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.all_notes.components.UsernameBox
import com.icdid.dashboard.presentation.components.NoteDialog
import com.icdid.dashboard.presentation.util.PreviewUtil
import com.icdid.dashboard.presentation.util.toInitials
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
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            NoteMarkTopAppBar(
                title = {
                    Text(
                        text = stringResource(com.icdid.core.presentation.R.string.notemark),
                        style = LocalNoteMarkTypography.current.titleMedium.copy(
                            lineHeight = 24.sp
                        )
                    )
                },
                actions = {
                    UsernameBox(
                        modifier = Modifier.padding(end = 16.dp), username = state.username
                    )
                }
            )
        },
        floatingActionButton = {
            NoteMarkFAB(
                onClick = {
                   // TODO create note api and db
                }
            )
        }
    ) { innerPadding ->
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)

        val isTablet = deviceType.isTablet()

        when (deviceType) {
            DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> AllNotesPortraitView(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(vertical = 12.dp, horizontal = 12.dp),
                isTablet = isTablet
            )

            DeviceType.TABLET_LANDSCAPE, DeviceType.MOBILE_LANDSCAPE -> AllNotesLandscapeView(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(vertical = 12.dp, horizontal = 12.dp),
                isTablet = isTablet
            )
        }

        NoteDialog(
            showDialog = state.showDeleteDialog,
            onDismiss = {
                onAction(AllNotesAction.OnLongNoteDisplayDialog)
            },
            onConfirm = {
                // TODO delete note
            },
            title = stringResource(R.string.delete_note),
            content = stringResource(R.string.delete_confirmation),
            confirmText = stringResource(R.string.delete),
            dismissText = stringResource(R.string.cancel)
        )
    }
}

@MobilePreviews
@TabletPreviews
@Composable
private fun Preview() {
    NoteMarkTheme {
        AllNotesScreen(
            state = AllNotesState(
                username = "Galah seno adjie santoso".toInitials(),
                notes = PreviewUtil.noteSamples,
                showDeleteDialog = false
            ), onAction = {})
    }
}