package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.domain.model.WorkoutLocation
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectableOptionCard

@Composable
fun LocationCard(
    onSelect: (WorkoutLocation) -> Unit,
    modifier: Modifier = Modifier
) {
    OnboardingCardContainer(
        title = stringResource(R.string.location_title),
        subtitle = stringResource(R.string.location_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectableOptionCard(
                icon = "🏠",
                title = stringResource(R.string.location_home_no_equipment),
                description = stringResource(R.string.location_home_no_equipment_desc),
                onClick = { onSelect(WorkoutLocation.HOME_NO_EQUIPMENT) }
            )

            SelectableOptionCard(
                icon = "🏋️",
                title = stringResource(R.string.location_home_with_equipment),
                description = stringResource(R.string.location_home_with_equipment_desc),
                onClick = { onSelect(WorkoutLocation.HOME_WITH_EQUIPMENT) }
            )

            SelectableOptionCard(
                icon = "🏢",
                title = stringResource(R.string.location_gym),
                description = stringResource(R.string.location_gym_desc),
                onClick = { onSelect(WorkoutLocation.GYM) }
            )

            SelectableOptionCard(
                icon = "🌳",
                title = stringResource(R.string.location_outdoor),
                description = stringResource(R.string.location_outdoor_desc),
                onClick = { onSelect(WorkoutLocation.OUTDOOR) }
            )
        }
    }
}
