package com.icdid.dashboard.presentation.note_detail

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.UiText
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.components.NoteDialog
import com.icdid.dashboard.presentation.note_detail.composables.NoteDetailModeFAB
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteDetailRoot(
    viewModel: NoteDetailViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        viewModel.onAction(NoteDetailAction.OnCloseClicked)
    }

    ObserveAsEvents(viewModel.event) { event ->
        keyboardController?.hide()

        when (event) {
            NoteDetailEvent.OnDiscardChanges -> {
                onNavigateBack()
            }

            is NoteDetailEvent.Error -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }

            NoteDetailEvent.NoteSaved -> {
                Toast.makeText(context, R.string.successfully_save_note, Toast.LENGTH_LONG).show()
                onNavigateBack()
            }
        }
    }

    NoteDetailScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun NoteDetailScreen(
    state: NoteDetailState,
    onAction: (NoteDetailAction) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)
    val isTablet = deviceType.isTablet()

    Scaffold(
        floatingActionButton = {
            if (!state.isNewNote) {
                NoteDetailModeFAB(
                    noteDetailMode = state.noteMode,
                    onAction = onAction,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (deviceType) {
                DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> NoteDetailPortraitView(
                    isTablet = isTablet,
                    state = state,
                    onAction = onAction,
                )

                DeviceType.TABLET_LANDSCAPE, DeviceType.MOBILE_LANDSCAPE -> NoteDetailLandscapeView(
                    isTablet = isTablet,
                    state = state,
                    onAction = onAction,
                )
            }
        }

        NoteDialog(
            showDialog = state.isSaveNoteDialogVisible,
            onDismiss = {
                onAction(NoteDetailAction.OnConfirmationDialogDismissed)
            },
            onConfirm = {
                onAction(NoteDetailAction.OnConfirmationDialogConfirmed)
            },
            title = stringResource(R.string.confirmation_dialog_title),
            content = stringResource(R.string.confirmation_dialog_body),
            confirmText = stringResource(R.string.confirmation_dialog_confirm),
            dismissText = stringResource(R.string.confirmation_dialog_dismiss),
        )
    }
}

@Preview
@Composable
fun NoteDetailScreenPreview() {
    NoteMarkTheme {
        NoteDetailScreen(
            state = NoteDetailState(
                title = "Note Title",
                createdAt = UiText.DynamicString("2021-01-01"),
                lastEditedAt = UiText.DynamicString("2021-01-01"),
                content = "Note Content"
            ),
            onAction = {}
        )
    }
}