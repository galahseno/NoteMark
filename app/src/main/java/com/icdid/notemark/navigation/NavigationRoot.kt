package com.icdid.notemark.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.icdid.auth.presentation.landing.LandingAction
import com.icdid.auth.presentation.landing.LandingScreen
import org.koin.androidx.compose.koinViewModel
import com.icdid.auth.presentation.login.LoginScreen
import com.icdid.auth.presentation.login.LoginScreenViewModel

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
                            navController.navigate(Screen.Auth.Login)
                        }
                    }
                }
            )
        }
        composable<Screen.Auth.Login> {
            val loginViewModel = koinViewModel<LoginScreenViewModel>()
            val state = loginViewModel.state.collectAsStateWithLifecycle().value

            LoginScreen(
                state = state,
                onAction = loginViewModel::onAction
            )
        }
        composable<Screen.Auth.Register> {

        }
    }
}