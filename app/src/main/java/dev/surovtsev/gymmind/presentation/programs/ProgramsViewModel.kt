package dev.surovtsev.gymmind.presentation.programs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.domain.model.ProgramModel
import dev.surovtsev.gymmind.domain.model.enums.ProgramDifficulty
import dev.surovtsev.gymmind.domain.model.enums.ProgramGoal
import dev.surovtsev.gymmind.domain.repository.ProgramRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramsViewModel @Inject constructor(
    private val programRepository: ProgramRepository
) : ViewModel() {

    private val _selectedFilter = MutableStateFlow<ProgramFilter>(ProgramFilter.All)
    val selectedFilter: StateFlow<ProgramFilter> = _selectedFilter.asStateFlow()

    val programs: StateFlow<List<ProgramModel>> = _selectedFilter
        .flatMapLatest { filter ->
            when (filter) {
                is ProgramFilter.All -> programRepository.getAllPrograms()
                is ProgramFilter.Free -> programRepository.getFreePrograms()
                is ProgramFilter.Premium -> programRepository.getPremiumPrograms()
                is ProgramFilter.Featured -> programRepository.getFeaturedPrograms()
                is ProgramFilter.ByDifficulty -> programRepository.getProgramsByDifficulty(filter.difficulty)
                is ProgramFilter.ByGoal -> programRepository.getProgramsByGoal(filter.goal)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setFilter(filter: ProgramFilter) {
        viewModelScope.launch {
            _selectedFilter.value = filter
        }
    }
}

sealed class ProgramFilter {
    object All : ProgramFilter()
    object Free : ProgramFilter()
    object Premium : ProgramFilter()
    object Featured : ProgramFilter()
    data class ByDifficulty(val difficulty: ProgramDifficulty) : ProgramFilter()
    data class ByGoal(val goal: ProgramGoal) : ProgramFilter()
}
