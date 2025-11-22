package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.core.theme.TextSecondary
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingSlider

@Composable
fun TargetWeightCard(
    currentWeight: Float,
    onNext: (targetWeight: Float) -> Unit,
    modifier: Modifier = Modifier
) {
    // Default target weight based on current weight
    var targetWeight by remember {
        mutableStateOf(currentWeight)
    }

    val difference = targetWeight - currentWeight

    OnboardingCardContainer(
        title = stringResource(R.string.target_weight_title),
        subtitle = stringResource(R.string.target_weight_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Current weight info
            Text(
                text = stringResource(R.string.target_weight_current, currentWeight),
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )

            // Target weight slider
            OnboardingSlider(
                value = targetWeight,
                onValueChange = { targetWeight = it },
                valueRange = (currentWeight - 30f).coerceAtLeast(30f)..(currentWeight + 30f).coerceAtMost(200f),
                label = stringResource(R.string.target_weight_label),
                valueFormatter = { "%.1f kg".format(it) }
            )

            // Weight difference
            if (difference != 0f) {
                Text(
                    text = stringResource(R.string.target_weight_difference, difference),
                    style = MaterialTheme.typography.titleMedium,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // Continue button
        GymMindButton(
            text = stringResource(R.string.continue_button),
            onClick = { onNext(targetWeight) }
        )
    }
}
