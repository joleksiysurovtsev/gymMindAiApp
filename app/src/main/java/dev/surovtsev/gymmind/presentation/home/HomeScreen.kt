package dev.surovtsev.gymmind.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.core.theme.Accent
import dev.surovtsev.gymmind.core.theme.Primary
import dev.surovtsev.gymmind.core.theme.TextSecondary
import dev.surovtsev.gymmind.domain.model.WorkoutCategory
import dev.surovtsev.gymmind.domain.model.WorkoutPlan
import dev.surovtsev.gymmind.presentation.components.AnimatedNeonBackground
import dev.surovtsev.gymmind.presentation.components.SignInRequiredDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val isGuestMode by viewModel.isGuestMode.collectAsState()
    val workouts by viewModel.workouts.collectAsState()
    var showSignInDialog by remember { mutableStateOf(false) }

    AnimatedNeonBackground(
        intensity = 0.5f,
        enableGrid = true,
        enableRings = false,
        enableCornerDots = false
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "GymMind",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(Routes.PROFILE) }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                    )
                )
            },
            containerColor = androidx.compose.ui.graphics.Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Guest Mode Banner
                if (isGuestMode) {
                    GuestModeBanner(
                        onSignInClick = { navController.navigate(Routes.PROFILE) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Workouts List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        Text(
                            text = "Your Workouts",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(workouts) { workout ->
                        WorkoutCard(
                            workout = workout,
                            isLocked = isGuestMode && workout.category == WorkoutCategory.PREMIUM,
                            onClick = {
                                if (isGuestMode && workout.category == WorkoutCategory.PREMIUM) {
                                    showSignInDialog = true
                                } else {
                                    // Navigate to workout detail (not implemented yet)
                                    // navController.navigate(Routes.workoutExecution(workout.id))
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    // Sign In Required Dialog
    if (showSignInDialog) {
        SignInRequiredDialog(
            onDismiss = { showSignInDialog = false },
            onSignInClick = {
                showSignInDialog = false
                navController.navigate(Routes.PROFILE)
            }
        )
    }
}

@Composable
fun GuestModeBanner(
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Primary.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Guest Mode",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sign in for unlimited workouts",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            TextButton(onClick = onSignInClick) {
                Text("Sign In", color = Primary)
            }
        }
    }
}

@Composable
fun WorkoutCard(
    workout: WorkoutPlan,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = workout.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = workout.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            maxLines = 2
                        )
                    }

                    if (isLocked) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = Accent.copy(alpha = 0.2f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Premium",
                                    modifier = Modifier.size(14.dp),
                                    tint = Accent
                                )
                                Text(
                                    text = "Premium",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Accent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoChip(
                        text = "${workout.durationMinutes} min",
                        icon = "⏱️"
                    )
                    InfoChip(
                        text = workout.difficulty.name,
                        icon = "💪"
                    )
                    InfoChip(
                        text = "${workout.caloriesBurnEstimate} kcal",
                        icon = "🔥"
                    )
                }

                // Tags
                if (workout.tags.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        workout.tags.take(3).forEach { tag ->
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = Primary.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Lock overlay for premium workouts
            if (isLocked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        modifier = Modifier.size(48.dp),
                        tint = Accent.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoChip(text: String, icon: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = icon, style = MaterialTheme.typography.bodySmall)
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
    }
}
