package com.example.splashscreen.Nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.splashscreen.ui.OnboardingScreen
import com.example.splashscreen.ui.SplashScreen

@Composable
fun MyAppNav(modifier: Modifier = Modifier){

    val navController = rememberNavController()

    NavHost(navController= navController, startDestination = Routes.splash){
        composable(Routes.splash){
            SplashScreen(navController)
        }
        composable(Routes.onboarding){
            OnboardingScreen(navController)
        }

    }

}