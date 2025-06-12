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
import com.icdid.auth.presentation.login.LoginAction
import org.koin.androidx.compose.koinViewModel
import com.icdid.auth.presentation.login.LoginScreen
import com.icdid.auth.presentation.login.LoginScreenViewModel
import com.icdid.auth.presentation.register.RegisterRoot

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
                            navController.navigate(Screen.Auth.Register) {
                                popUpTo(Screen.Auth.Landing) {
                                    inclusive = true
                                }
                            }
                        }
                        LandingAction.OnLoginClicked -> {
                            navController.navigate(Screen.Auth.Login) {
                                popUpTo(Screen.Auth.Landing) {
                                    inclusive = true
                                }
                            }
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
                onAction = { action ->
                    when(action) {
                        LoginAction.OnRegisterClicked -> {
                            navController.navigate(Screen.Auth.Register) {
                                popUpTo<Screen.Auth.Login> {
                                    inclusive = true
                                    saveState = true
                                }
                                restoreState = true
                            }
                        }
                        else -> loginViewModel.onAction(action)
                    }
                }
            )
        }
        composable<Screen.Auth.Register> {
            RegisterRoot(
                onNavigateToLogin = {
                    navController.navigate(Screen.Auth.Login) {
                        popUpTo<Screen.Auth.Register> {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}