package com.icdid.auth.presentation.register

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.auth.presentation.R
import com.icdid.core.presentation.composables.NoteMarkDefaultScreen
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePreviews
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.SnackbarAction
import com.icdid.core.presentation.utils.SnackbarController
import com.icdid.core.presentation.utils.SnackbarEvent
import com.icdid.core.presentation.utils.TabletPreviews
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRoot(
    onNavigateToLogin: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->
        keyboardController?.hide()

        when (event) {
            is RegisterEvent.Error -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = event.error.asString(context),
                        )
                    )
                }
            }

            RegisterEvent.RegistrationSuccess -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = context.getString(R.string.registration_successful),
                            action = SnackbarAction(
                                name = context.getString(R.string.ok),
                                action = {
                                    onSuccessfulRegistration()
                                }
                            ),
                            onDismiss = {
                                onSuccessfulRegistration()
                            }
                        )
                    )
                }
            }
        }
    }

    RegisterScreen(
        state = state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnLoginClicked -> onNavigateToLogin()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)

    val isTablet = deviceType.isTablet()

    NoteMarkDefaultScreen {
        when (deviceType) {
            DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> RegisterPortraitView(
                isTablet = isTablet,
                state = state,
                onAction = onAction,
            )

            DeviceType.TABLET_LANDSCAPE, DeviceType.MOBILE_LANDSCAPE -> RegisterLandscapeView(
                state = state,
                onAction = onAction,
            )
        }
    }
}

@MobilePreviews
@TabletPreviews
@Composable
fun RegisterScreenPreview() {
    NoteMarkTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = { }
        )
    }
}