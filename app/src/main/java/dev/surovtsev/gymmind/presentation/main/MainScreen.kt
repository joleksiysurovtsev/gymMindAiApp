package dev.surovtsev.gymmind.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.presentation.coach.AICoachScreen
import dev.surovtsev.gymmind.presentation.components.GymMindBottomNavigationBar
import dev.surovtsev.gymmind.presentation.home.HomeScreen
import dev.surovtsev.gymmind.presentation.programs.ProgramsScreen
import dev.surovtsev.gymmind.presentation.settings.SettingsScreen
import dev.surovtsev.gymmind.core.theme.Background

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            GymMindBottomNavigationBar(navController = navController)
        },
        containerColor = Background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen()
            }

            composable(Routes.PROGRAMS) {
                ProgramsScreen()
            }

            composable(Routes.AI_COACH) {
                AICoachScreen()
            }

            composable(Routes.SETTINGS) {
                SettingsScreen()
            }
        }
    }
}
