package com.icdid.notemark.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.icdid.auth.presentation.landing.LandingAction
import com.icdid.auth.presentation.landing.LandingScreen
import com.icdid.auth.presentation.login.LoginRoot
import com.icdid.auth.presentation.register.RegisterRoot
import com.icdid.dashboard.presentation.all_notes.AllNotesRoot
import com.icdid.dashboard.presentation.note_detail.NoteDetailRoot
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
        homeGraph(navController)
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
            LoginRoot(
                onNavigateToRegister = {
                    navController.navigate(Screen.Auth.Register) {
                        popUpTo<Screen.Auth.Login> {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulLogin = {
                    navController.navigate(Screen.Home) {
                        popUpTo<Screen.Auth.Login> {
                            inclusive = true
                            saveState = true
                        }
                    }
                },
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
                },
                onSuccessfulRegistration = {
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

private fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<Screen.Home>(
        startDestination = Screen.Home.AllNotes
    ) {
        composable<Screen.Home.AllNotes> {
            AllNotesRoot(
                onNavigateToNoteDetail = {
                    navController.navigate(Screen.Home.NoteDetail(it))
                }
            )
        }

        composable<Screen.Home.NoteDetail> {
            val noteId = it.toRoute<Screen.Home.NoteDetail>().noteId
            NoteDetailRoot(
                onNavigateBack = {
                    navController.navigateUp()
                },
                viewModel = koinViewModel(
                    parameters = { parametersOf(noteId) }
                )
            )
        }
    }
}