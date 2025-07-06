package com.icdid.dashboard.presentation.note_detail

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.core.presentation.composables.FadeVisibilityComponent
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.UiText
import com.icdid.core.presentation.utils.applyIf
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.components.NoteDialog
import com.icdid.dashboard.presentation.note_detail.composables.NoteDetailModeFAB
import com.icdid.dashboard.presentation.note_detail.model.NoteDetailMode
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
    val activity = LocalView.current.context as? Activity
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)
    val isTablet = deviceType.isTablet()

    when (state.noteMode) {
        NoteDetailMode.READ -> {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        else -> activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    Scaffold(
        floatingActionButton = {
            if (state.noteMode != NoteDetailMode.EDIT) {
                FadeVisibilityComponent(
                    isVisible = state.areUiElementsVisible,
                ) {
                    NoteDetailModeFAB(
                        noteDetailMode = state.noteMode,
                        onAction = onAction,
                        isVisible = state.areUiElementsVisible,
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .applyIf(state.noteMode == NoteDetailMode.READ) {
                    pointerInput(Unit) {
                        detectTapGestures {
                            onAction(NoteDetailAction.OnReadModeTap)
                        }
                    }
                }
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

    DisposableEffect(Unit) {
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
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