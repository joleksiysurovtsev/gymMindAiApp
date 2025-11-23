package dev.surovtsev.gymmind.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.model.ProgramModel
import dev.surovtsev.gymmind.domain.repository.ProgramRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val programRepository: ProgramRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val isGuestMode: StateFlow<Boolean> = userPreferences.isGuestMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _activeProgram = MutableStateFlow<ProgramModel?>(null)
    val activeProgram: StateFlow<ProgramModel?> = _activeProgram.asStateFlow()

    private val _currentWeek = MutableStateFlow(1)
    val currentWeek: StateFlow<Int> = _currentWeek.asStateFlow()

    private val _completedWorkouts = MutableStateFlow(0)
    val completedWorkouts: StateFlow<Int> = _completedWorkouts.asStateFlow()

    init {
        loadActiveProgram()
    }

    private fun loadActiveProgram() {
        viewModelScope.launch {
            // Mock: загружаем первую бесплатную программу как активную
            // В будущем это будет загружаться из базы данных по userId
            programRepository.getAllPrograms()
                .first()
                .firstOrNull { !it.isPremium }
                ?.let { program ->
                    _activeProgram.value = program
                    // Mock progress data
                    _currentWeek.value = 3
                    _completedWorkouts.value = 8
                }
        }
    }

    fun calculateProgress(): Float {
        val program = _activeProgram.value ?: return 0f
        val totalWeeks = program.duration.weeks ?: 1
        return _currentWeek.value.toFloat() / totalWeeks.toFloat()
    }

    fun selectProgram(programId: String) {
        viewModelScope.launch {
            programRepository.getProgramById(programId)?.let { program ->
                _activeProgram.value = program
                _currentWeek.value = 1
                _completedWorkouts.value = 0
            }
        }
    }

    fun completeWorkout() {
        _completedWorkouts.value += 1
    }
}
