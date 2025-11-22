package dev.surovtsev.gymmind.core.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val GymMindColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    secondary = Accent,
    tertiary = AccentAlt,
    background = BgPrimary,
    onBackground = TextPrimary,
    surface = BgSurface,
    onSurface = TextPrimary,
    surfaceVariant = BgSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    error = Error,
    onError = TextPrimary,
    outline = Divider
)

@Composable
fun GymMindTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GymMindColorScheme,
        typography = GymMindTypography,
        shapes = GymMindShapes,
        content = content
    )
}
