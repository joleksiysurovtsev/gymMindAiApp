package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.domain.model.HealthLimitation
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingTextField
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectableChip

@Composable
fun LimitationsCard(
    onNext: (limitations: List<HealthLimitation>, custom: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLimitations by remember { mutableStateOf(emptyList<HealthLimitation>()) }
    var customText by remember { mutableStateOf("") }
    var customError by remember { mutableStateOf<String?>(null) }

    // Validation message
    val customRequiredMsg = stringResource(R.string.limitation_other_required)

    val isValid = selectedLimitations.isNotEmpty() &&
            (HealthLimitation.OTHER !in selectedLimitations || customText.isNotBlank())

    OnboardingCardContainer(
        title = stringResource(R.string.limitations_title),
        subtitle = stringResource(R.string.limitations_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SelectableChip(
                        text = stringResource(R.string.limitation_none),
                        isSelected = HealthLimitation.NONE in selectedLimitations,
                        onClick = {
                            selectedLimitations = if (HealthLimitation.NONE in selectedLimitations) {
                                emptyList()
                            } else {
                                listOf(HealthLimitation.NONE)
                            }
                        }
                    )

                    SelectableChip(
                        text = stringResource(R.string.limitation_back_issues),
                        isSelected = HealthLimitation.BACK_ISSUES in selectedLimitations,
                        onClick = {
                            selectedLimitations = if (HealthLimitation.BACK_ISSUES in selectedLimitations) {
                                selectedLimitations.filter { it != HealthLimitation.BACK_ISSUES }
                            } else {
                                (selectedLimitations.filter { it != HealthLimitation.NONE } + HealthLimitation.BACK_ISSUES)
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SelectableChip(
                        text = stringResource(R.string.limitation_knee_issues),
                        isSelected = HealthLimitation.KNEE_ISSUES in selectedLimitations,
                        onClick = {
                            selectedLimitations = if (HealthLimitation.KNEE_ISSUES in selectedLimitations) {
                                selectedLimitations.filter { it != HealthLimitation.KNEE_ISSUES }
                            } else {
                                (selectedLimitations.filter { it != HealthLimitation.NONE } + HealthLimitation.KNEE_ISSUES)
                            }
                        }
                    )

                    SelectableChip(
                        text = stringResource(R.string.limitation_shoulder_issues),
                        isSelected = HealthLimitation.SHOULDER_ISSUES in selectedLimitations,
                        onClick = {
                            selectedLimitations = if (HealthLimitation.SHOULDER_ISSUES in selectedLimitations) {
                                selectedLimitations.filter { it != HealthLimitation.SHOULDER_ISSUES }
                            } else {
                                (selectedLimitations.filter { it != HealthLimitation.NONE } + HealthLimitation.SHOULDER_ISSUES)
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SelectableChip(
                        text = stringResource(R.string.limitation_cardiovascular),
                        isSelected = HealthLimitation.CARDIOVASCULAR in selectedLimitations,
                        onClick = {
                            selectedLimitations = if (HealthLimitation.CARDIOVASCULAR in selectedLimitations) {
                                selectedLimitations.filter { it != HealthLimitation.CARDIOVASCULAR }
                            } else {
                                (selectedLimitations.filter { it != HealthLimitation.NONE } + HealthLimitation.CARDIOVASCULAR)
                            }
                        }
                    )

                    SelectableChip(
                        text = stringResource(R.string.limitation_pregnancy),
                        isSelected = HealthLimitation.PREGNANCY in selectedLimitations,
                        onClick = {
                            selectedLimitations = if (HealthLimitation.PREGNANCY in selectedLimitations) {
                                selectedLimitations.filter { it != HealthLimitation.PREGNANCY }
                            } else {
                                (selectedLimitations.filter { it != HealthLimitation.NONE } + HealthLimitation.PREGNANCY)
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SelectableChip(
                        text = stringResource(R.string.limitation_other),
                        isSelected = HealthLimitation.OTHER in selectedLimitations,
                        onClick = {
                            selectedLimitations = if (HealthLimitation.OTHER in selectedLimitations) {
                                selectedLimitations.filter { it != HealthLimitation.OTHER }
                            } else {
                                (selectedLimitations.filter { it != HealthLimitation.NONE } + HealthLimitation.OTHER)
                            }
                        }
                    )
                }
            }

            // Custom limitation text field
            if (HealthLimitation.OTHER in selectedLimitations) {
                OnboardingTextField(
                    value = customText,
                    onValueChange = {
                        customText = it
                        customError = null
                    },
                    label = stringResource(R.string.limitation_other),
                    hint = stringResource(R.string.limitation_other_hint),
                    keyboardType = KeyboardType.Text,
                    isError = customError != null,
                    errorMessage = customError
                )
            }
        }

        GymMindButton(
            text = stringResource(R.string.continue_button),
            onClick = {
                if (HealthLimitation.OTHER in selectedLimitations && customText.isBlank()) {
                    customError = customRequiredMsg
                } else {
                    val custom = if (HealthLimitation.OTHER in selectedLimitations) customText else null
                    onNext(selectedLimitations, custom)
                }
            },
            enabled = isValid
        )
    }
}
