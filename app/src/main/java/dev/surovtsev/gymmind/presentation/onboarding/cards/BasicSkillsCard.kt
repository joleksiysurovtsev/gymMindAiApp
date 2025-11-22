package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.ToggleOption

@Composable
fun BasicSkillsCard(
    onNext: (squats: Boolean, pushUps: Boolean, plank: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var canDoSquats by remember { mutableStateOf(false) }
    var canDoPushUps by remember { mutableStateOf(false) }
    var canDoPlank by remember { mutableStateOf(false) }

    OnboardingCardContainer(
        title = stringResource(R.string.basic_skills_title),
        subtitle = stringResource(R.string.basic_skills_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ToggleOption(
                text = stringResource(R.string.basic_skills_squats),
                isChecked = canDoSquats,
                onCheckedChange = { canDoSquats = it }
            )

            ToggleOption(
                text = stringResource(R.string.basic_skills_pushups),
                isChecked = canDoPushUps,
                onCheckedChange = { canDoPushUps = it }
            )

            ToggleOption(
                text = stringResource(R.string.basic_skills_plank),
                isChecked = canDoPlank,
                onCheckedChange = { canDoPlank = it }
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        GymMindButton(
            text = stringResource(R.string.continue_button),
            onClick = { onNext(canDoSquats, canDoPushUps, canDoPlank) }
        )
    }
}
