package com.icdid.auth.presentation.register

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdid.core.presentation.composables.NoteMarkDefaultScreen
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.core.presentation.utils.MobilePreviews
import com.icdid.core.presentation.utils.ObserveAsEvents
import com.icdid.core.presentation.utils.TabletPreviews
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->

    }

    RegisterScreen(
        state = state,
        onAction = { action ->
            when(action) {
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