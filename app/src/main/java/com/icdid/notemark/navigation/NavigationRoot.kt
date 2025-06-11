package com.icdid.notemark.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icdid.auth.presentation.landing.LandingAction
import com.icdid.auth.presentation.landing.LandingScreen

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Home else Screen.Auth,
        modifier = Modifier.fillMaxSize()
    ) {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<Screen.Auth>(
        startDestination = Screen.Auth.Landing
    ) {
        composable<Screen.Auth.Landing> {
            LandingScreen(
                onAction = { action ->
                    when(action) {
                        LandingAction.OnGettingStartedClicked -> {
                            // Handle navigate getting started
                        }
                        LandingAction.OnLoginClicked -> {
                            // Handle navigate login
                        }
                    }
                }
            )
        }
        composable<Screen.Auth.Login> {

        }
        composable<Screen.Auth.Register> {

        }
    }
}