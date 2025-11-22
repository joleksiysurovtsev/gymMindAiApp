package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.domain.model.DaysPerWeek
import dev.surovtsev.gymmind.domain.model.MinutesPerWorkout
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectableChip

@Composable
fun ScheduleCard(
    onNext: (days: DaysPerWeek, minutes: MinutesPerWorkout) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDays by remember { mutableStateOf<DaysPerWeek?>(null) }
    var selectedMinutes by remember { mutableStateOf<MinutesPerWorkout?>(null) }

    val isValid = selectedDays != null && selectedMinutes != null

    OnboardingCardContainer(
        title = stringResource(R.string.schedule_title),
        subtitle = stringResource(R.string.schedule_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Days per week
            Text(
                text = stringResource(R.string.schedule_days_label),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableChip(
                    text = stringResource(R.string.schedule_days_2_3),
                    isSelected = selectedDays == DaysPerWeek.TWO_THREE,
                    onClick = { selectedDays = DaysPerWeek.TWO_THREE },
                    modifier = Modifier.weight(1f)
                )

                SelectableChip(
                    text = stringResource(R.string.schedule_days_4_5),
                    isSelected = selectedDays == DaysPerWeek.FOUR_FIVE,
                    onClick = { selectedDays = DaysPerWeek.FOUR_FIVE },
                    modifier = Modifier.weight(1f)
                )

                SelectableChip(
                    text = stringResource(R.string.schedule_days_6_7),
                    isSelected = selectedDays == DaysPerWeek.SIX_SEVEN,
                    onClick = { selectedDays = DaysPerWeek.SIX_SEVEN },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Workout duration
            Text(
                text = stringResource(R.string.schedule_duration_label),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableChip(
                    text = stringResource(R.string.schedule_duration_short),
                    isSelected = selectedMinutes == MinutesPerWorkout.SHORT,
                    onClick = { selectedMinutes = MinutesPerWorkout.SHORT },
                    modifier = Modifier.weight(1f)
                )

                SelectableChip(
                    text = stringResource(R.string.schedule_duration_medium),
                    isSelected = selectedMinutes == MinutesPerWorkout.MEDIUM,
                    onClick = { selectedMinutes = MinutesPerWorkout.MEDIUM },
                    modifier = Modifier.weight(1f)
                )

                SelectableChip(
                    text = stringResource(R.string.schedule_duration_long),
                    isSelected = selectedMinutes == MinutesPerWorkout.LONG,
                    onClick = { selectedMinutes = MinutesPerWorkout.LONG },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        GymMindButton(
            text = stringResource(R.string.continue_button),
            onClick = {
                if (selectedDays != null && selectedMinutes != null) {
                    onNext(selectedDays!!, selectedMinutes!!)
                }
            },
            enabled = isValid
        )
    }
}
