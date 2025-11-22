package dev.surovtsev.gymmind.presentation.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.core.theme.Divider
import dev.surovtsev.gymmind.core.theme.Primary
import dev.surovtsev.gymmind.core.theme.TextDisabled
import dev.surovtsev.gymmind.domain.model.CardType
import dev.surovtsev.gymmind.presentation.components.AnimatedNeonBackground
import dev.surovtsev.gymmind.presentation.onboarding.cards.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val currentCard by viewModel.currentCard.collectAsState()
    val canGoBack by viewModel.canGoBack.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()

    // Handle navigation events
    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is OnboardingNavigationEvent.NavigateToHome -> {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.ONBOARDING) { inclusive = true }
                }
                viewModel.clearNavigationEvent()
            }
            null -> { /* No navigation event */ }
        }
    }

    // Show loading or error overlay
    when (uiState) {
        is OnboardingUiState.Saving -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return
        }
        is OnboardingUiState.Error -> {
            val errorMessage = (uiState as OnboardingUiState.Error).message
            AlertDialog(
                onDismissRequest = { /* Cannot dismiss */ },
                title = { Text(stringResource(R.string.error_generic)) },
                text = { Text(errorMessage) },
                confirmButton = {
                    TextButton(onClick = { viewModel.retryCompletion() }) {
                        Text("Retry")
                    }
                }
            )
        }
        else -> { /* Continue rendering */ }
    }

    AnimatedNeonBackground(
        intensity = 0.6f,
        enableGrid = false,
        enableRings = false,
        enableCornerDots = true
    ) {
        Scaffold(
            topBar = {
                OnboardingTopBar(
                    progress = progress,
                    canGoBack = canGoBack,
                    onBackClick = { viewModel.goToPreviousCard() }
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                AnimatedContent(
                    targetState = currentCard,
                    transitionSpec = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300)
                        ) + fadeIn() with
                                slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(300)
                                ) + fadeOut()
                    },
                    label = "card_transition"
                ) { card ->
                    when (card) {
                        CardType.BASIC_INFO -> BasicInfoCard(
                            onNext = { gender, age, height, weight ->
                                viewModel.updateBasicInfo(gender, age, height, weight)
                                viewModel.goToNextCard()
                            }
                        )

                        CardType.GOAL -> GoalCard(
                            onSelect = { goal ->
                                viewModel.updateGoal(goal)
                                viewModel.goToNextCard()
                            }
                        )

                        CardType.TARGET_WEIGHT -> {
                            val currentWeight = viewModel.userProfile.value.currentWeight ?: 70f
                            TargetWeightCard(
                                currentWeight = currentWeight,
                                onNext = { weight ->
                                    viewModel.updateTargetWeight(weight)
                                    viewModel.goToNextCard()
                                }
                            )
                        }

                        CardType.EXPERIENCE -> ExperienceCard(
                            onSelect = { level ->
                                viewModel.updateExperienceLevel(level)
                                viewModel.goToNextCard()
                            }
                        )

                        CardType.LOCATION -> LocationCard(
                            onSelect = { location ->
                                viewModel.updateLocation(location)
                                viewModel.goToNextCard()
                            }
                        )

                        CardType.EQUIPMENT -> EquipmentCard(
                            onNext = { equipment ->
                                viewModel.updateEquipment(equipment)
                                viewModel.goToNextCard()
                            }
                        )

                        CardType.SCHEDULE -> ScheduleCard(
                            onNext = { days, minutes ->
                                viewModel.updateSchedule(days, minutes)
                                viewModel.goToNextCard()
                            }
                        )

                        CardType.LIMITATIONS -> LimitationsCard(
                            onNext = { limitations, custom ->
                                viewModel.updateLimitations(limitations, custom)
                                viewModel.goToNextCard()
                            }
                        )

                        CardType.BASIC_SKILLS -> BasicSkillsCard(
                            onNext = { squats, pushUps, plank ->
                                viewModel.updateBasicSkills(squats, pushUps, plank)
                                viewModel.goToNextCard()
                            }
                        )

                        CardType.NOTIFICATIONS -> NotificationsCard(
                            onComplete = { time ->
                                viewModel.updateNotificationTime(time)
                                viewModel.goToNextCard()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingTopBar(
    progress: Float,
    canGoBack: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                enabled = canGoBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = if (canGoBack) Primary else Color.Transparent
                )
            }

            Text(
                text = stringResource(R.string.onboarding_title),
                style = MaterialTheme.typography.titleMedium
            )

            // Placeholder for symmetry
            Spacer(modifier = Modifier.size(48.dp))
        }

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = Primary,
            trackColor = Divider
        )
    }
}
