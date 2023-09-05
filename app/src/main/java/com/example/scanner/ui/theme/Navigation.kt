package com.example.scanner.ui.theme

import androidx.compose.runtime.Composable

sealed class Navigation(val route: String){
    object LandingScreen : Navigation("landing_screen")
    object MainScreen : Navigation("main_screen")
}