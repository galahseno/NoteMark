package com.icdid.dashboard.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdid.core.presentation.composables.NoteMarkDefaultScreen
import com.icdid.core.presentation.composables.NoteMarkTopAppBar
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.SnackbarController
import com.icdid.core.presentation.utils.SnackbarEvent
import com.icdid.dashboard.presentation.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    ObserveAsEvents(viewModel.event) { event ->
        when(event) {
            SettingsEvent.LogOutSuccess -> onNavigateToLogin()
            is SettingsEvent.LogOutError -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = event.error.asString(context)
                        )
                    )
                }
            }
        }
    }

    SettingsScreen(
        onAction = { action ->
            when(action) {
                SettingsAction.OnBackClick -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        },
    )
}

@Composable
fun SettingsScreen(
    onAction: (SettingsAction) -> Unit = {}
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)

    val horizontalPadding = when (deviceType) {
        DeviceType.MOBILE_PORTRAIT -> { 16.dp }
        DeviceType.TABLET_PORTRAIT -> { 24.dp }
        else -> { 60.dp }
    }

    NoteMarkDefaultScreen(
        topAppBar = {
            NoteMarkTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings).uppercase(),
                        style = LocalNoteMarkTypography.current.titleXSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(
                            start = horizontalPadding - 16.dp
                        ),
                        onClick = { onAction(SettingsAction.OnBackClick) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onAction(SettingsAction.OnLogOutClick)
                    }
                    .padding(
                        start = horizontalPadding,
                        top = 16.dp,
                        bottom = 16.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Log out",
                    tint = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.size(12.dp))

                Text(
                    text = stringResource(R.string.log_out),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    NoteMarkTheme {
        SettingsScreen()
    }
}