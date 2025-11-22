package dev.surovtsev.gymmind.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.core.theme.Accent
import dev.surovtsev.gymmind.core.theme.Error
import dev.surovtsev.gymmind.core.theme.Primary
import dev.surovtsev.gymmind.core.theme.PrimaryBright
import dev.surovtsev.gymmind.core.theme.TextSecondary
import dev.surovtsev.gymmind.presentation.components.AnimatedNeonBackground
import dev.surovtsev.gymmind.presentation.components.GymMindButton

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    val navigationEvent = viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navigationEvent.value) {
        when (navigationEvent.value) {
            is SplashNavigationEvent.NavigateToOnboarding -> {
                navController.navigate(Routes.ONBOARDING) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            is SplashNavigationEvent.NavigateToHome -> {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            null -> {}
        }
    }

    SplashContent(
        uiState = uiState.value,
        onSignInClick = { viewModel.signInWithGoogle(context) },
        onGuestClick = { viewModel.continueAsGuest() }
    )
}

@Composable
fun SplashContent(
    uiState: SplashUiState = SplashUiState.Loading,
    onSignInClick: () -> Unit = {},
    onGuestClick: () -> Unit = {}
) {
    // Анимации для логотипа
    val infiniteTransition = rememberInfiniteTransition(label = "splash")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val textAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1500, delayMillis = 300),
        label = "textAlpha"
    )

    // Используем переиспользуемый фон
    AnimatedNeonBackground(
        intensity = 1.0f,
        enableGrid = true,
        enableRings = true,
        enableCornerDots = false,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Центральный контент с логотипом
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .alpha(textAlpha)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                // Логотип с неоновым glow эффектом
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    // Glow эффект (2 слоя)
                    Image(
                        painter = painterResource(id = R.drawable.logo_runner),
                        contentDescription = "GymMind Logo",
                        modifier = Modifier
                            .size(200.dp)
                            .scale(pulseScale * 1.2f)
                            .alpha(0.3f),
                        colorFilter = ColorFilter.tint(Accent)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo_runner),
                        contentDescription = "GymMind Logo",
                        modifier = Modifier
                            .size(180.dp)
                            .scale(pulseScale * 1.1f)
                            .alpha(0.5f),
                        colorFilter = ColorFilter.tint(Primary)
                    )
                    // Основной логотип
                    Image(
                        painter = painterResource(id = R.drawable.logo_runner),
                        contentDescription = "GymMind Logo",
                        modifier = Modifier
                            .size(160.dp)
                            .scale(pulseScale),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                // Название с градиентом
                Text(
                    text = "GymMind",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 52.sp,
                        letterSpacing = 2.sp,
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = Primary,
                            offset = Offset(0f, 0f),
                            blurRadius = 20f
                        )
                    ),
                    color = Color.White
                )

                // AI Badge
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Primary, PrimaryBright)
                            ),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "AI POWERED",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        ),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your Personal Fitness Coach",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            // Нижняя часть с кнопкой логина или индикатором загрузки
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is SplashUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = Primary,
                            strokeWidth = 3.dp
                        )
                    }
                    is SplashUiState.NotAuthenticated -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Sign in with Google - Glass morphism style
                            Surface(
                                onClick = onSignInClick,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp),
                                shape = MaterialTheme.shapes.large,
                                color = Color.Transparent,
                                border = BorderStroke(
                                    width = 2.dp,
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.4f),
                                            Color.White.copy(alpha = 0.2f)
                                        )
                                    )
                                ),
                                shadowElevation = 8.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    Color.White.copy(alpha = 0.15f),
                                                    Color.White.copy(alpha = 0.08f)
                                                ),
                                                start = Offset(0f, 0f),
                                                end = Offset(1000f, 1000f)
                                            )
                                        )
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    Primary.copy(alpha = 0.12f),
                                                    Color.Transparent
                                                ),
                                                center = Offset(0f, 0f),
                                                radius = 400f
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    ) {
                                        // Google Icon (custom)
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .background(
                                                    Color.White,
                                                    shape = androidx.compose.foundation.shape.CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "G",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF4285F4) // Google blue
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = "Sign In",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                }
                            }

                            // Continue as Guest - Glass morphism style
                            Surface(
                                onClick = onGuestClick,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp),
                                shape = MaterialTheme.shapes.large,
                                color = Color.Transparent,
                                border = BorderStroke(
                                    width = 2.dp,
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.4f),
                                            Color.White.copy(alpha = 0.2f)
                                        )
                                    )
                                ),
                                shadowElevation = 8.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    Color.White.copy(alpha = 0.15f),
                                                    Color.White.copy(alpha = 0.08f)
                                                ),
                                                start = Offset(0f, 0f),
                                                end = Offset(1000f, 1000f)
                                            )
                                        )
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    Accent.copy(alpha = 0.12f),
                                                    Color.Transparent
                                                ),
                                                center = Offset(200f, 200f),
                                                radius = 400f
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PersonOutline,
                                            contentDescription = "Guest",
                                            modifier = Modifier.size(28.dp),
                                            tint = Color.White.copy(alpha = 0.9f)
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = "Guest",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is SplashUiState.Authenticating -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Primary,
                                strokeWidth = 3.dp
                            )
                            Text(
                                text = "Signing in...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                    }
                    is SplashUiState.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = uiState.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Error,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    } // закрывает AnimatedNeonBackground
}
