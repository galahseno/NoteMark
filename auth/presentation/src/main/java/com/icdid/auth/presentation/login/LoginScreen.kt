package com.icdid.auth.presentation.login

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.core.presentation.composables.NoteMarkDefaultScreen
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePreviews
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.SnackbarController
import com.icdid.core.presentation.utils.SnackbarEvent
import com.icdid.core.presentation.utils.TabletPreviews
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    onNavigateToRegister: () -> Unit,
    onSuccessfulLogin: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->
        keyboardController?.hide()

        when(event) {
            is LoginEvent.Error -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = event.error.asString(context)
                        )
                    )
                }
            }

            LoginEvent.LoginSuccess -> {
                onSuccessfulLogin()
            }
        }
    }

    LoginScreen(
        state = state,
        onAction = { action ->
            when(action) {
                LoginAction.OnRegisterClicked -> onNavigateToRegister()
                else -> viewModel.onAction(action)
            }
        }
    )
}


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState = LoginState(),
    onAction: (LoginAction) -> Unit = {}
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)

    val isTablet = deviceType.isTablet()

    NoteMarkDefaultScreen(
        isFromAuthGraph = true,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        when (deviceType) {
            DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> LoginPortraitView(
                isTablet = isTablet,
                state = state,
                onAction = onAction,
            )
            DeviceType.TABLET_LANDSCAPE, DeviceType.MOBILE_LANDSCAPE -> LoginLandscapeView(
                state = state,
                onAction = onAction,

            )
        }
    }
}

@MobilePreviews
@TabletPreviews
@Composable
private fun LoginScreenTabletPreview() {
    NoteMarkTheme {
        LoginScreen()
    }
}