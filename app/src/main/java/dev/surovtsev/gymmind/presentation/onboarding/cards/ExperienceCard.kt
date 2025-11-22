package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.domain.model.ExperienceLevel
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectableOptionCard

@Composable
fun ExperienceCard(
    onSelect: (ExperienceLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    OnboardingCardContainer(
        title = stringResource(R.string.experience_title),
        subtitle = stringResource(R.string.experience_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectableOptionCard(
                icon = "🌱",
                title = stringResource(R.string.experience_beginner),
                description = stringResource(R.string.experience_beginner_desc),
                onClick = { onSelect(ExperienceLevel.BEGINNER) }
            )

            SelectableOptionCard(
                icon = "🏃",
                title = stringResource(R.string.experience_intermediate),
                description = stringResource(R.string.experience_intermediate_desc),
                onClick = { onSelect(ExperienceLevel.INTERMEDIATE) }
            )

            SelectableOptionCard(
                icon = "🏆",
                title = stringResource(R.string.experience_advanced),
                description = stringResource(R.string.experience_advanced_desc),
                onClick = { onSelect(ExperienceLevel.ADVANCED) }
            )
        }
    }
}
