package dev.surovtsev.gymmind.presentation.onboarding.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.domain.model.enums.Equipment
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import dev.surovtsev.gymmind.presentation.onboarding.components.OnboardingCardContainer
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectableChip

@Composable
fun EquipmentCard(
    onNext: (equipment: List<Equipment>) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedEquipment by remember { mutableStateOf(emptyList<Equipment>()) }

    OnboardingCardContainer(
        title = stringResource(R.string.equipment_title),
        subtitle = stringResource(R.string.equipment_subtitle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableChip(
                    text = stringResource(R.string.equipment_dumbbells),
                    isSelected = Equipment.DUMBBELLS in selectedEquipment,
                    onClick = {
                        selectedEquipment = if (Equipment.DUMBBELLS in selectedEquipment) {
                            selectedEquipment.filter { it != Equipment.DUMBBELLS }
                        } else {
                            selectedEquipment + Equipment.DUMBBELLS
                        }
                    }
                )

                SelectableChip(
                    text = stringResource(R.string.equipment_barbell),
                    isSelected = Equipment.BARBELL in selectedEquipment,
                    onClick = {
                        selectedEquipment = if (Equipment.BARBELL in selectedEquipment) {
                            selectedEquipment.filter { it != Equipment.BARBELL }
                        } else {
                            selectedEquipment + Equipment.BARBELL
                        }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableChip(
                    text = stringResource(R.string.equipment_pull_up_bar),
                    isSelected = Equipment.PULL_UP_BAR in selectedEquipment,
                    onClick = {
                        selectedEquipment = if (Equipment.PULL_UP_BAR in selectedEquipment) {
                            selectedEquipment.filter { it != Equipment.PULL_UP_BAR }
                        } else {
                            selectedEquipment + Equipment.PULL_UP_BAR
                        }
                    }
                )

                SelectableChip(
                    text = stringResource(R.string.equipment_resistance_bands),
                    isSelected = Equipment.RESISTANCE_BAND in selectedEquipment,
                    onClick = {
                        selectedEquipment = if (Equipment.RESISTANCE_BAND in selectedEquipment) {
                            selectedEquipment.filter { it != Equipment.RESISTANCE_BAND }
                        } else {
                            selectedEquipment + Equipment.RESISTANCE_BAND
                        }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableChip(
                    text = stringResource(R.string.equipment_bench),
                    isSelected = Equipment.BENCH in selectedEquipment,
                    onClick = {
                        selectedEquipment = if (Equipment.BENCH in selectedEquipment) {
                            selectedEquipment.filter { it != Equipment.BENCH }
                        } else {
                            selectedEquipment + Equipment.BENCH
                        }
                    }
                )

                SelectableChip(
                    text = stringResource(R.string.equipment_machines),
                    isSelected = Equipment.MACHINES in selectedEquipment,
                    onClick = {
                        selectedEquipment = if (Equipment.MACHINES in selectedEquipment) {
                            selectedEquipment.filter { it != Equipment.MACHINES }
                        } else {
                            selectedEquipment + Equipment.MACHINES
                        }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableChip(
                    text = stringResource(R.string.equipment_cardio_machines),
                    isSelected = Equipment.CARDIO_MACHINES in selectedEquipment,
                    onClick = {
                        selectedEquipment = if (Equipment.CARDIO_MACHINES in selectedEquipment) {
                            selectedEquipment.filter { it != Equipment.CARDIO_MACHINES }
                        } else {
                            selectedEquipment + Equipment.CARDIO_MACHINES
                        }
                    }
                )
            }
        }

        GymMindButton(
            text = stringResource(R.string.continue_button),
            onClick = { onNext(selectedEquipment) }
        )
    }
}
