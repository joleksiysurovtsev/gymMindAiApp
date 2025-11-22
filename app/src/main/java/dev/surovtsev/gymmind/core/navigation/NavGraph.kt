package dev.surovtsev.gymmind.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.surovtsev.gymmind.presentation.home.HomeScreen
import dev.surovtsev.gymmind.presentation.onboarding.OnboardingScreen
import dev.surovtsev.gymmind.presentation.splash.SplashScreen

@Composable
fun GymMindNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }

        composable(Routes.ONBOARDING) {
            OnboardingScreen(navController = navController)
        }

        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }

        // Остальные экраны будут добавлены позже
    }
}
