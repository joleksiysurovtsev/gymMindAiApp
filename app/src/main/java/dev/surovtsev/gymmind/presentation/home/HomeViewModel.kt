package dev.surovtsev.gymmind.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.model.WorkoutPlan
import dev.surovtsev.gymmind.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val isGuestMode: StateFlow<Boolean> = userPreferences.isGuestMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val workouts: StateFlow<List<WorkoutPlan>> = workoutRepository.getAllWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
