package com.icdid.notemark.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.icdid.dashboard.presentation.settings.SettingsRoot

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
        startDestination = Screen.Auth.Landing()
    ) {
        composable<Screen.Auth.Landing>(
            exitTransition = {
                slideOutVertically(targetOffsetY = { -it / 2 }) + fadeOut()
            },
        ) {
            val navArgs = it.toRoute<Screen.Auth.Landing>()

            LaunchedEffect(Unit) {
                if (navArgs.isSkipped) {
                    navController.navigate(Screen.Auth.Login) {
                        popUpTo<Screen.Auth.Landing> {
                            inclusive = true
                        }
                    }
                }
            }

            LandingScreen(
                onAction = { action ->
                    when (action) {
                        LandingAction.OnGettingStartedClicked -> {
                            navController.navigate(Screen.Auth.Register) {
                                popUpTo<Screen.Auth.Landing> {
                                    inclusive = true
                                }
                            }
                        }

                        LandingAction.OnLoginClicked -> {
                            navController.navigate(Screen.Auth.Login) {
                                popUpTo<Screen.Auth.Landing> {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            )
        }
        composable<Screen.Auth.Login>(
            enterTransition = {
                slideInVertically(initialOffsetY = { it }) + fadeIn()
            },
        ) {
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
        composable<Screen.Auth.Register>(
            enterTransition = {
                slideInVertically(initialOffsetY = { it }) + fadeIn()
            },
        ) {
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
        composable<Screen.Home.AllNotes>(
            popEnterTransition = {
                slideInVertically(initialOffsetY = { -it / 2 }) + fadeIn()
            },
        ) {
            AllNotesRoot(
                onNavigateToNoteDetail = {
                    navController.navigate(Screen.Home.NoteDetail(noteId = it))
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Home.Settings)
                },
                onSuccessSavedNoted = {
                    navController.navigate(Screen.Home.NoteDetail(isNewNote = true, noteId = it))
                }
            )
        }

        composable<Screen.Home.NoteDetail>(
            enterTransition = {
                slideInVertically(initialOffsetY = { it })
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { it })
            },
        ) {
            NoteDetailRoot(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.Home.Settings> {
            SettingsRoot(
                onNavigateToLogin = {
                    navController.navigate(Screen.Auth.Landing(isSkipped = true)) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}