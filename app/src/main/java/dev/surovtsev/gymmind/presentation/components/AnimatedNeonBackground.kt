package dev.surovtsev.gymmind.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.core.theme.*
import kotlin.math.sin

/**
 * Анимированный неоновый фон с множественными источниками света
 *
 * @param modifier Модификатор для контейнера
 * @param intensity Интенсивность эффектов (0.0f - 1.0f). По умолчанию 1.0f
 * @param enableGrid Включить футуристичную сетку. По умолчанию true
 * @param enableRings Включить неоновые кольца в центре. По умолчанию true
 * @param enableCornerDots Включить декоративные точки по углам. По умолчанию true
 * @param content Контент поверх фона
 */
@Composable
fun AnimatedNeonBackground(
    modifier: Modifier = Modifier,
    intensity: Float = 1.0f,
    enableGrid: Boolean = true,
    enableRings: Boolean = true,
    enableCornerDots: Boolean = false,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "neon_bg")

    // Множественные источники света с разными ритмами
    val light1Pulse by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "light1"
    )

    val light2Pulse by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, delayMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "light2"
    )

    val light3Pulse by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, delayMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "light3"
    )

    val light4Pulse by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, delayMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "light4"
    )

    val sweepRotation by infiniteTransition.animateFloat(
        initialValue = -45f,
        targetValue = 45f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sweep"
    )

    val ringScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring"
    )

    val ringAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ringAlpha"
    )

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Слой 1: Базовый темный фон
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000000))
            )

            // Слой 2: Diagonal sweep градиент
            Canvas(modifier = Modifier.fillMaxSize()) {
                val sweepCenter = Offset(size.width / 2, size.height / 2)
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Primary.copy(alpha = 0.05f * intensity),
                            Color.Transparent
                        ),
                        start = Offset(
                            x = sweepCenter.x - size.width * sin(Math.toRadians(sweepRotation.toDouble())).toFloat(),
                            y = 0f
                        ),
                        end = Offset(
                            x = sweepCenter.x + size.width * sin(Math.toRadians(sweepRotation.toDouble())).toFloat(),
                            y = size.height
                        )
                    )
                )
            }

            // Слой 3: Источник света 1 - левый верхний угол (Primary Blue)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val light1Center = Offset(size.width * 0.15f, size.height * 0.2f)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.25f * light1Pulse * intensity),
                            Primary.copy(alpha = 0.12f * light1Pulse * intensity),
                            Color.Transparent
                        ),
                        center = light1Center,
                        radius = size.minDimension * 0.6f
                    ),
                    center = light1Center,
                    radius = size.minDimension * 0.6f
                )
            }

            // Слой 4: Источник света 2 - правый нижний угол (Accent Cyan)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val light2Center = Offset(size.width * 0.85f, size.height * 0.75f)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Accent.copy(alpha = 0.3f * light2Pulse * intensity),
                            Accent.copy(alpha = 0.15f * light2Pulse * intensity),
                            Color.Transparent
                        ),
                        center = light2Center,
                        radius = size.minDimension * 0.7f
                    ),
                    center = light2Center,
                    radius = size.minDimension * 0.7f
                )
            }

            // Слой 5: Источник света 3 - правый верхний угол (AccentAlt Green)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val light3Center = Offset(size.width * 0.9f, size.height * 0.15f)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            AccentAlt.copy(alpha = 0.2f * light3Pulse * intensity),
                            AccentAlt.copy(alpha = 0.1f * light3Pulse * intensity),
                            Color.Transparent
                        ),
                        center = light3Center,
                        radius = size.minDimension * 0.5f
                    ),
                    center = light3Center,
                    radius = size.minDimension * 0.5f
                )
            }

            // Слой 6: Источник света 4 - нижний центр (Primary Bright)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val light4Center = Offset(size.width * 0.5f, size.height * 0.9f)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            PrimaryBright.copy(alpha = 0.18f * light4Pulse * intensity),
                            PrimaryBright.copy(alpha = 0.08f * light4Pulse * intensity),
                            Color.Transparent
                        ),
                        center = light4Center,
                        radius = size.minDimension * 0.55f
                    ),
                    center = light4Center,
                    radius = size.minDimension * 0.55f
                )
            }

            // Слой 7: Тонкий вертикальный градиент для объема
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                BgSurface.copy(alpha = 0.2f * intensity),
                                Color.Transparent,
                                BgPrimary.copy(alpha = 0.3f * intensity)
                            )
                        )
                    )
            )

            // Слой 8: Mesh overlay для футуристичности
            if (enableGrid) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val gridSize = 100.dp.toPx()
                    val strokeWidth = 0.5.dp.toPx()

                    var x = 0f
                    while (x < size.width) {
                        drawLine(
                            color = Primary.copy(alpha = 0.03f * intensity),
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = strokeWidth
                        )
                        x += gridSize
                    }

                    var y = 0f
                    while (y < size.height) {
                        drawLine(
                            color = Primary.copy(alpha = 0.03f * intensity),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                        y += gridSize
                    }
                }
            }

            // Контент поверх фона
            Box(modifier = Modifier.fillMaxSize()) {
                // Неоновые кольца в центре (опционально)
                if (enableRings) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val centerOffset = Offset(size.width / 2, size.height / 2)

                        drawCircle(
                            color = Primary.copy(alpha = 0.4f * ringAlpha * intensity),
                            radius = size.minDimension * 0.35f * ringScale,
                            center = centerOffset,
                            style = Stroke(width = 2.dp.toPx())
                        )

                        drawCircle(
                            color = Accent.copy(alpha = 0.35f * ringAlpha * intensity),
                            radius = size.minDimension * 0.25f * ringScale,
                            center = centerOffset,
                            style = Stroke(width = 2.5.dp.toPx())
                        )

                        drawCircle(
                            color = AccentAlt.copy(alpha = 0.3f * ringAlpha * intensity),
                            radius = size.minDimension * 0.15f * ringScale,
                            center = centerOffset,
                            style = Stroke(width = 3.dp.toPx())
                        )

                        drawCircle(
                            color = PrimaryBright.copy(alpha = 0.25f * ringAlpha * intensity),
                            radius = size.minDimension * 0.45f * ringScale,
                            center = centerOffset,
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                    }
                }

                // Декоративные точки по углам (опционально)
                if (enableCornerDots) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val baseDotRadius = 4.dp.toPx()
                        val glowRadius = 12.dp.toPx()
                        val padding = 40.dp.toPx()

                        // Верхний левый угол - Primary
                        val topLeftCenter = Offset(padding, padding)
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Primary.copy(alpha = 0.3f * light1Pulse * intensity),
                                    Color.Transparent
                                ),
                                center = topLeftCenter,
                                radius = glowRadius
                            ),
                            radius = glowRadius,
                            center = topLeftCenter
                        )
                        drawCircle(
                            color = Primary.copy(alpha = 0.8f * intensity),
                            radius = baseDotRadius,
                            center = topLeftCenter
                        )

                        // Верхний правый угол - AccentAlt
                        val topRightCenter = Offset(size.width - padding, padding)
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    AccentAlt.copy(alpha = 0.3f * light3Pulse * intensity),
                                    Color.Transparent
                                ),
                                center = topRightCenter,
                                radius = glowRadius
                            ),
                            radius = glowRadius,
                            center = topRightCenter
                        )
                        drawCircle(
                            color = AccentAlt.copy(alpha = 0.8f * intensity),
                            radius = baseDotRadius,
                            center = topRightCenter
                        )

                        // Нижний левый угол - AccentAlt
                        val bottomLeftCenter = Offset(padding, size.height - padding)
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    AccentAlt.copy(alpha = 0.3f * light1Pulse * intensity),
                                    Color.Transparent
                                ),
                                center = bottomLeftCenter,
                                radius = glowRadius
                            ),
                            radius = glowRadius,
                            center = bottomLeftCenter
                        )
                        drawCircle(
                            color = AccentAlt.copy(alpha = 0.8f * intensity),
                            radius = baseDotRadius,
                            center = bottomLeftCenter
                        )

                        // Нижний правый угол - Accent
                        val bottomRightCenter = Offset(size.width - padding, size.height - padding)
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Accent.copy(alpha = 0.4f * light2Pulse * intensity),
                                    Color.Transparent
                                ),
                                center = bottomRightCenter,
                                radius = glowRadius
                            ),
                            radius = glowRadius,
                            center = bottomRightCenter
                        )
                        drawCircle(
                            color = Accent.copy(alpha = 0.8f * intensity),
                            radius = baseDotRadius,
                            center = bottomRightCenter
                        )

                        // Маленькие точки вдоль краев
                        val smallDotRadius = 2.dp.toPx()
                        val smallDotAlpha = 0.4f * intensity

                        // Верхний край
                        for (i in 1..3) {
                            val x = size.width * (i / 4f)
                            drawCircle(
                                color = Primary.copy(alpha = smallDotAlpha * light1Pulse),
                                radius = smallDotRadius,
                                center = Offset(x, padding / 2)
                            )
                        }

                        // Нижний край
                        for (i in 1..3) {
                            val x = size.width * (i / 4f)
                            drawCircle(
                                color = Accent.copy(alpha = smallDotAlpha * light2Pulse),
                                radius = smallDotRadius,
                                center = Offset(x, size.height - padding / 2)
                            )
                        }
                    }
                }

                // Контент пользователя
                content()
            }
        }
    }
}
