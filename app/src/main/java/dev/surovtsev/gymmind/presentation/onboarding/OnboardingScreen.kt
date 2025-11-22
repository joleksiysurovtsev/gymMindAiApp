package dev.surovtsev.gymmind.presentation.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import dev.surovtsev.gymmind.presentation.components.AnimatedNeonBackground
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            val userId = userPreferences.userId.first()
            if (userId != null) {
                authRepository.saveOnboardingCompleted(userId)
                onComplete()
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    // Используем анимированный фон с меньшей интенсивностью
    AnimatedNeonBackground(
        intensity = 0.6f,
        enableGrid = false,
        enableRings = false,
        enableCornerDots = true
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
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Onboarding",
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                // Title
                Text(
                    text = "Onboarding",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                // Description
                Text(
                    text = "This is a placeholder for the onboarding flow.\n\nThe full 7-step onboarding questionnaire\nwill be implemented later.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Skip Button
                GymMindButton(
                    text = "Skip for now",
                    onClick = {
                        scope.launch {
                            viewModel.completeOnboarding {
                                navController.navigate(Routes.HOME) {
                                    popUpTo(Routes.ONBOARDING) { inclusive = true }
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
