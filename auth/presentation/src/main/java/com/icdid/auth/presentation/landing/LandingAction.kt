package com.icdid.auth.presentation.landing

sealed interface LandingAction {
    data object OnGettingStartedClicked : LandingAction
    data object OnLoginClicked : LandingAction
}