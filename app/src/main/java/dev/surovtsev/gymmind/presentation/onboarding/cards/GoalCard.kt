package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.domain.model.FitnessGoal
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectableOptionCard

@Composable
fun GoalCard(
    onSelect: (FitnessGoal) -> Unit,
    modifier: Modifier = Modifier
) {
    OnboardingCardContainer(
        title = stringResource(R.string.goal_title),
        subtitle = stringResource(R.string.goal_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectableOptionCard(
                icon = "🔥",
                title = stringResource(R.string.goal_lose_weight),
                description = stringResource(R.string.goal_lose_weight_desc),
                onClick = { onSelect(FitnessGoal.LOSE_WEIGHT) }
            )

            SelectableOptionCard(
                icon = "💪",
                title = stringResource(R.string.goal_gain_muscle),
                description = stringResource(R.string.goal_gain_muscle_desc),
                onClick = { onSelect(FitnessGoal.GAIN_MUSCLE) }
            )

            SelectableOptionCard(
                icon = "🧘",
                title = stringResource(R.string.goal_improve_flexibility),
                description = stringResource(R.string.goal_improve_flexibility_desc),
                onClick = { onSelect(FitnessGoal.IMPROVE_FLEXIBILITY) }
            )

            SelectableOptionCard(
                icon = "⚡",
                title = stringResource(R.string.goal_improve_endurance),
                description = stringResource(R.string.goal_improve_endurance_desc),
                onClick = { onSelect(FitnessGoal.IMPROVE_ENDURANCE) }
            )

            SelectableOptionCard(
                icon = "🎯",
                title = stringResource(R.string.goal_maintain_fitness),
                description = stringResource(R.string.goal_maintain_fitness_desc),
                onClick = { onSelect(FitnessGoal.MAINTAIN_FITNESS) }
            )
        }
    }
}
