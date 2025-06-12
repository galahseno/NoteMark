package com.icdid.auth.presentation.login

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.icdid.core.presentation.composables.NoteMarkDefaultScreen
import com.icdid.core.presentation.model.DeviceType
import com.icdid.core.presentation.utils.MobilePreviews
import com.icdid.core.presentation.utils.TabletPreviews

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState = LoginState(),
    onAction: (LoginAction) -> Unit = {}
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceType.fromWindowsSizeClass(windowSizeClass)

    val isTablet = deviceType.isTablet()

    NoteMarkDefaultScreen {
        when (deviceType) {
            DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> LoginPortraitView(
                isTablet = isTablet,
                email = state.email,
                password = state.password,
                isLoginButtonEnabled = state.isLoginButtonEnabled,
                isLoading = state.loading,
                onEmailTextChange = { onAction(LoginAction.OnEmailTextChange(it)) },
                onPasswordTextChange = { onAction(LoginAction.OnPasswordTextChange(it)) },
            )
            DeviceType.TABLET_LANDSCAPE, DeviceType.MOBILE_LANDSCAPE -> LoginLandscapeView(
                email = state.email,
                password = state.password,
                isLoginButtonEnabled = state.isLoginButtonEnabled,
                isLoading = state.loading,
                onEmailTextChange = { onAction(LoginAction.OnEmailTextChange(it)) },
                onPasswordTextChange = { onAction(LoginAction.OnPasswordTextChange(it)) },
            )
        }
    }
}

@MobilePreviews
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

@TabletPreviews
@Composable
fun LoginScreenTabletPortraitPreview() {
    LoginScreen()
}