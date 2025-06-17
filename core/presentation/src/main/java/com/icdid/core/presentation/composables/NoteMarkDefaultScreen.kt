package com.icdid.core.presentation.composables

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.icdid.core.presentation.theme.LocalNoteMarkTypography
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.SnackbarController
import kotlinx.coroutines.launch

@Composable
fun NoteMarkDefaultScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            when (result) {
                SnackbarResult.ActionPerformed -> {
                    event.action?.action?.invoke()
                }

                SnackbarResult.Dismissed -> {
                    event.onDismiss?.invoke()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .padding(8.dp),
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        action = {
                            if (!data.visuals.actionLabel.isNullOrBlank()) {
                                TextButton(
                                    onClick = { data.performAction() },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(
                                        text = data.visuals.actionLabel.orEmpty(),
                                        style = LocalNoteMarkTypography.current.bodyMedium
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = data.visuals.message,
                            style = LocalNoteMarkTypography.current.bodySmall
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->

        val view = LocalView.current
        val paddingTop = innerPadding.calculateTopPadding() + 8.dp

        Box(
            modifier = modifier
                .padding(top = paddingTop)
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                )
        ) {
            content()
        }

        SideEffect {
            val window = (view.context as? Activity)?.window
            if (!view.isInEditMode && window != null) {
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
                    false
            }
        }
    }
}