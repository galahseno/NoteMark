package com.icdid.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.icdid.core.presentation.theme.NoteMarkTheme
import com.icdid.notemark.navigation.NavigationRoot
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                NoteMarkTheme {
                    val navController = rememberNavController()

                    NavigationRoot(
                        navController = navController,
                        isLoggedIn = false,
                    )
                }
            }
        }
    }
}