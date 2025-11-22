package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.domain.model.NotificationTime
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectableOptionCard

@Composable
fun NotificationsCard(
    onComplete: (NotificationTime) -> Unit,
    modifier: Modifier = Modifier
) {
    OnboardingCardContainer(
        title = stringResource(R.string.notifications_title),
        subtitle = stringResource(R.string.notifications_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectableOptionCard(
                icon = "🌅",
                title = stringResource(R.string.notification_time_morning),
                description = stringResource(R.string.notification_time_morning_desc),
                onClick = { onComplete(NotificationTime.MORNING) }
            )

            SelectableOptionCard(
                icon = "☀️",
                title = stringResource(R.string.notification_time_afternoon),
                description = stringResource(R.string.notification_time_afternoon_desc),
                onClick = { onComplete(NotificationTime.AFTERNOON) }
            )

            SelectableOptionCard(
                icon = "🌙",
                title = stringResource(R.string.notification_time_evening),
                description = stringResource(R.string.notification_time_evening_desc),
                onClick = { onComplete(NotificationTime.EVENING) }
            )

            SelectableOptionCard(
                icon = "🔔",
                title = stringResource(R.string.notification_time_anytime),
                description = stringResource(R.string.notification_time_anytime_desc),
                onClick = { onComplete(NotificationTime.ANYTIME) }
            )

            SelectableOptionCard(
                icon = "🔕",
                title = stringResource(R.string.notification_time_none),
                description = stringResource(R.string.notification_time_none_desc),
                onClick = { onComplete(NotificationTime.NONE) }
            )
        }
    }
}
