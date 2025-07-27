package com.icdid.dashboard.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.core.domain.model.SyncInterval
import com.icdid.core.presentation.composables.NoteMarkDefaultScreen
import com.icdid.core.presentation.composables.NoteMarkTopAppBar
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.SnackbarController
import com.icdid.core.presentation.utils.SnackbarEvent
import com.icdid.core.presentation.utils.applyIf
import com.icdid.core.presentation.utils.crop
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.components.NoteDialog
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

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->
        when (event) {
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

            is SettingsEvent.SyncError -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = event.error.asString(context)
                        )
                    )
                }
            }

            SettingsEvent.SyncSuccess -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = context.getString(R.string.sync_success)
                        )
                    )
                }
            }
        }
    }

    SettingsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                SettingsAction.OnBackClick -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        },
    )
}

@Composable
fun SettingsScreen(
    state: SettingState,
    onAction: (SettingsAction) -> Unit = {}
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)

    val horizontalPadding = when (deviceType) {
        DeviceType.MOBILE_PORTRAIT -> {
            16.dp
        }

        DeviceType.TABLET_PORTRAIT -> {
            24.dp
        }

        else -> {
            60.dp
        }
    }

    val density = LocalDensity.current
    var rowSize by remember { mutableStateOf(IntSize.Zero) }

    NoteMarkDefaultScreen(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
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
                .padding(horizontal = horizontalPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        onAction(SettingsAction.OnSyncIntervalClick)
                    }
                    .onSizeChanged {
                        rowSize = it
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = stringResource(R.string.sync_interval),
                )

                Text(
                    text = stringResource(R.string.sync_interval),
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text =
                        when (state.syncInterval) {
                            SyncInterval.Manual -> stringResource(R.string.manual_only)
                            SyncInterval.FifteenMinutes -> stringResource(R.string._15_minutes)
                            SyncInterval.ThirtyMinutes -> stringResource(R.string._30_minutes)
                            SyncInterval.OneHour -> stringResource(R.string._1_hour)
                        },
                    style = MaterialTheme.typography.bodyLarge
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = stringResource(R.string.sync_interval),
                    modifier = Modifier
                        .size(12.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = 0,
                                y = with(density) {
                                    rowSize.height.toDp().roundToPx()
                                }
                            )
                        }
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                ) {

                    if (state.isSyncIntervalDialogVisible) {
                        DropdownMenu(
                            shape = RoundedCornerShape(16.dp),
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            modifier = Modifier
                                .widthIn(min = 190.dp)
                                .applyIf(deviceType == DeviceType.MOBILE_LANDSCAPE) {
                                    heightIn(max = 190.dp)
                                }
                                .crop(vertical = 8.dp),
                            expanded = true,
                            onDismissRequest = {
                                onAction(SettingsAction.OnSyncIntervalDialogDismiss)
                            }
                        ) {
                            SyncInterval.entries.forEach { interval ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            when (interval) {
                                                SyncInterval.Manual -> stringResource(R.string.manual_only)
                                                SyncInterval.FifteenMinutes -> stringResource(R.string._15_minutes)
                                                SyncInterval.ThirtyMinutes -> stringResource(R.string._30_minutes)
                                                SyncInterval.OneHour -> stringResource(R.string._1_hour)
                                            },
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    },
                                    trailingIcon = if (interval == state.syncInterval) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    } else {
                                        null
                                    },
                                    onClick = {
                                        onAction(
                                            SettingsAction.OnSyncIntervalDialogClick(interval)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surface,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        onAction(SettingsAction.OnManualSyncClick)
                    },
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Sync,
                    contentDescription = stringResource(R.string.sync_data),
                    modifier = Modifier
                        .rotate(65f)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.sync_data),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(
                            R.string.last_sync_s_min_ago,
                            state.lastSync.asString()
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surface,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        onAction(SettingsAction.OnLogOutClick)
                    },
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

        NoteDialog(
            showDialog = state.isUnsyncedDialogVisible,
            onDismiss = {
                onAction(SettingsAction.OnLogoutDialogDismiss)
            },
            onConfirm = {
                onAction(SettingsAction.OnLogoutDialogConfirm)
            },
            title = stringResource(R.string.are_you_sure_to_logout),
            content = stringResource(R.string.you_have_unsynced_changes_what_would_you_like_to_do_before_logging_out),
            confirmText = stringResource(R.string.sync_now),
            dismissText = stringResource(R.string.log_out_without_syncing)
        )

        if (state.isLoading) {
            AlertDialog(
                modifier = Modifier.sizeIn(maxWidth = 125.dp, maxHeight = 125.dp),
                onDismissRequest = {},
                confirmButton = {},
                dismissButton = {},
                title = null,
                text = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = true
                ),
                containerColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    NoteMarkTheme {
        SettingsScreen(
            state = SettingState(),
            onAction = {}
        )
    }
}