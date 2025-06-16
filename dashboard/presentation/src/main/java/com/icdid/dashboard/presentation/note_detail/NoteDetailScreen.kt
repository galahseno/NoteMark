package com.icdid.dashboard.presentation.note_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.components.NoteDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteDetailRoot(
    viewModel: NoteDetailViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) {
        when (it) {
            NoteDetailEvent.OnDiscardChanges -> {
                onNavigateBack()
            }
        }
    }

    NoteDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is NoteDetailAction.OnConfirmationDialogConfirmed -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun NoteDetailScreen(
    state: NoteDetailState = NoteDetailState(),
    onAction: (NoteDetailAction) -> Unit = {},
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)
    val isTablet = deviceType.isTablet()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold { innerPadding ->
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
                    focusRequester = focusRequester
                )

                DeviceType.TABLET_LANDSCAPE, DeviceType.MOBILE_LANDSCAPE -> NoteDetailLandscapeView(
                    state = state,
                    onAction = onAction,
                    focusRequester = focusRequester
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
fun NoteDetailScreenPreview(modifier: Modifier = Modifier) {
    NoteMarkTheme {
        NoteDetailScreen()
    }
}