package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.domain.model.Gender
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingTextField
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectableChip

@Composable
fun BasicInfoCard(
    onNext: (gender: Gender, age: Int, height: Int, weight: Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGender by remember { mutableStateOf<Gender?>(null) }
    var ageText by remember { mutableStateOf("") }
    var heightText by remember { mutableStateOf("") }
    var weightText by remember { mutableStateOf("") }

    var ageError by remember { mutableStateOf<String?>(null) }
    var heightError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }

    // Validation messages
    val ageRequiredMsg = stringResource(R.string.validation_age_required)
    val ageInvalidMsg = stringResource(R.string.validation_age_invalid)
    val heightRequiredMsg = stringResource(R.string.validation_height_required)
    val heightInvalidMsg = stringResource(R.string.validation_height_invalid)
    val weightRequiredMsg = stringResource(R.string.validation_weight_required)
    val weightInvalidMsg = stringResource(R.string.validation_weight_invalid)

    val isValid = selectedGender != null &&
            ageText.toIntOrNull() != null &&
            heightText.toIntOrNull() != null &&
            weightText.toFloatOrNull() != null

    OnboardingCardContainer(
        title = stringResource(R.string.basic_info_title),
        subtitle = stringResource(R.string.basic_info_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Gender selection
            Text(
                text = stringResource(R.string.gender_label),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableChip(
                    text = stringResource(R.string.gender_male),
                    isSelected = selectedGender == Gender.MALE,
                    onClick = { selectedGender = Gender.MALE },
                    modifier = Modifier.weight(1f)
                )
                SelectableChip(
                    text = stringResource(R.string.gender_female),
                    isSelected = selectedGender == Gender.FEMALE,
                    onClick = { selectedGender = Gender.FEMALE },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableChip(
                    text = stringResource(R.string.gender_other),
                    isSelected = selectedGender == Gender.OTHER,
                    onClick = { selectedGender = Gender.OTHER },
                    modifier = Modifier.weight(1f)
                )
                SelectableChip(
                    text = stringResource(R.string.gender_prefer_not_to_say),
                    isSelected = selectedGender == Gender.PREFER_NOT_TO_SAY,
                    onClick = { selectedGender = Gender.PREFER_NOT_TO_SAY },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Age input
            OnboardingTextField(
                value = ageText,
                onValueChange = {
                    ageText = it
                    ageError = null
                },
                label = stringResource(R.string.age_label),
                hint = stringResource(R.string.age_hint),
                keyboardType = KeyboardType.Number,
                isError = ageError != null,
                errorMessage = ageError
            )

            // Height input
            OnboardingTextField(
                value = heightText,
                onValueChange = {
                    heightText = it
                    heightError = null
                },
                label = stringResource(R.string.height_label),
                hint = stringResource(R.string.height_hint),
                keyboardType = KeyboardType.Number,
                isError = heightError != null,
                errorMessage = heightError
            )

            // Weight input
            OnboardingTextField(
                value = weightText,
                onValueChange = {
                    weightText = it
                    weightError = null
                },
                label = stringResource(R.string.weight_label),
                hint = stringResource(R.string.weight_hint),
                keyboardType = KeyboardType.Decimal,
                isError = weightError != null,
                errorMessage = weightError
            )
        }

        // Continue button
        GymMindButton(
            text = stringResource(R.string.continue_button),
            onClick = {
                val age = ageText.toIntOrNull()
                val height = heightText.toIntOrNull()
                val weight = weightText.toFloatOrNull()

                // Validation
                var hasError = false

                if (age == null) {
                    ageError = ageRequiredMsg
                    hasError = true
                } else if (age < 13 || age > 120) {
                    ageError = ageInvalidMsg
                    hasError = true
                }

                if (height == null) {
                    heightError = heightRequiredMsg
                    hasError = true
                } else if (height < 100 || height > 250) {
                    heightError = heightInvalidMsg
                    hasError = true
                }

                if (weight == null) {
                    weightError = weightRequiredMsg
                    hasError = true
                } else if (weight < 30 || weight > 300) {
                    weightError = weightInvalidMsg
                    hasError = true
                }

                if (!hasError && selectedGender != null && age != null && height != null && weight != null) {
                    onNext(selectedGender!!, age, height, weight)
                }
            },
            enabled = isValid
        )
    }
}
