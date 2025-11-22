package dev.surovtsev.gymmind.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import dev.surovtsev.gymmind.presentation.components.AnimatedNeonBackground
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            authRepository.signOut()
            onLogoutComplete()
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    // Используем анимированный фон с умеренной интенсивностью
    AnimatedNeonBackground(
        intensity = 0.7f,
        enableGrid = true,
        enableRings = false,
        enableCornerDots = false
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Icon
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                // Title
                Text(
                    text = "Welcome to GymMind!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                // Description
                Text(
                    text = "This is the Home Screen placeholder.\n\nThe full dashboard with workout programs,\nAI coach, and progress tracking\nwill be implemented later.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Logout Button
                GymMindButton(
                    text = "Logout",
                    onClick = {
                        scope.launch {
                            viewModel.logout {
                                navController.navigate(Routes.SPLASH) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
