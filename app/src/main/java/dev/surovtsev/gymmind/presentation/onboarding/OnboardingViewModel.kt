package dev.surovtsev.gymmind.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.domain.model.CardType
import dev.surovtsev.gymmind.domain.model.DaysPerWeek
import dev.surovtsev.gymmind.domain.model.FitnessGoal
import dev.surovtsev.gymmind.domain.model.HealthLimitation
import dev.surovtsev.gymmind.domain.model.MinutesPerWorkout
import dev.surovtsev.gymmind.domain.model.NotificationTime
import dev.surovtsev.gymmind.domain.model.UserProfile
import dev.surovtsev.gymmind.domain.model.WorkoutLocation
import dev.surovtsev.gymmind.domain.model.ExperienceLevel
import dev.surovtsev.gymmind.domain.model.enums.Equipment
import dev.surovtsev.gymmind.domain.model.enums.Gender
import dev.surovtsev.gymmind.domain.repository.OnboardingRepository
import dev.surovtsev.gymmind.domain.usecase.onboarding.CalculateProgressUseCase
import dev.surovtsev.gymmind.domain.usecase.onboarding.GetNextCardUseCase
import dev.surovtsev.gymmind.domain.usecase.onboarding.GetPreviousCardUseCase
import dev.surovtsev.gymmind.domain.usecase.onboarding.SaveOnboardingDataUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val onboardingRepository: OnboardingRepository,
    private val getNextCardUseCase: GetNextCardUseCase,
    private val getPreviousCardUseCase: GetPreviousCardUseCase,
    private val calculateProgressUseCase: CalculateProgressUseCase,
    private val saveOnboardingDataUseCase: SaveOnboardingDataUseCase
) : ViewModel() {

    // Initialize user profile from current auth user
    private val initialProfile = firebaseAuth.currentUser?.let { user ->
        UserProfile(
            id = user.uid,
            email = user.email ?: "",
            displayName = user.displayName ?: "",
            photoUrl = user.photoUrl?.toString()
        )
    } ?: UserProfile()

    private val _userProfile = MutableStateFlow(initialProfile)
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _currentCard = MutableStateFlow(CardType.BASIC_INFO)
    val currentCard: StateFlow<CardType> = _currentCard.asStateFlow()

    private val _canGoBack = MutableStateFlow(false)
    val canGoBack: StateFlow<Boolean> = _canGoBack.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _navigationEvent = MutableStateFlow<OnboardingNavigationEvent?>(null)
    val navigationEvent: StateFlow<OnboardingNavigationEvent?> = _navigationEvent.asStateFlow()

    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Idle)
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        loadSavedProgress()
        updateProgress()
    }

    private fun loadSavedProgress() {
        viewModelScope.launch {
            onboardingRepository.getOnboardingProgress().first()?.let { savedProfile ->
                _userProfile.value = savedProfile
                // TODO: Calculate which card to show based on saved data
            }
        }
    }

    // === Card 1: Basic Info ===
    fun updateBasicInfo(gender: Gender, age: Int, height: Int, weight: Float) {
        _userProfile.value = _userProfile.value.copy(
            gender = gender,
            age = age,
            height = height,
            currentWeight = weight
        )
        autoSaveProgress()
    }

    // === Card 2: Goal ===
    fun updateGoal(goal: FitnessGoal) {
        _userProfile.value = _userProfile.value.copy(goal = goal)
        autoSaveProgress()
    }

    // === Card 3: Target Weight ===
    fun updateTargetWeight(weight: Float) {
        _userProfile.value = _userProfile.value.copy(targetWeight = weight)
        autoSaveProgress()
    }

    // === Card 4: Experience ===
    fun updateExperienceLevel(level: ExperienceLevel) {
        _userProfile.value = _userProfile.value.copy(experienceLevel = level)
        autoSaveProgress()
    }

    // === Card 5: Location ===
    fun updateLocation(location: WorkoutLocation) {
        _userProfile.value = _userProfile.value.copy(location = location)
        autoSaveProgress()
    }

    // === Card 6: Equipment ===
    fun updateEquipment(equipment: List<Equipment>) {
        _userProfile.value = _userProfile.value.copy(equipment = equipment)
        autoSaveProgress()
    }

    // === Card 7: Schedule ===
    fun updateSchedule(days: DaysPerWeek, minutes: MinutesPerWorkout) {
        _userProfile.value = _userProfile.value.copy(
            daysPerWeek = days,
            minutesPerWorkout = minutes
        )
        autoSaveProgress()
    }

    // === Card 8: Limitations ===
    fun updateLimitations(limitations: List<HealthLimitation>, custom: String? = null) {
        _userProfile.value = _userProfile.value.copy(
            limitations = limitations,
            customLimitation = custom
        )
        autoSaveProgress()
    }

    // === Card 9: Basic Skills ===
    fun updateBasicSkills(squats: Boolean, pushUps: Boolean, plank: Boolean) {
        _userProfile.value = _userProfile.value.copy(
            canDoSquats = squats,
            canDoPushUps = pushUps,
            canDoPlank = plank
        )
        autoSaveProgress()
    }

    // === Card 10: Notifications ===
    fun updateNotificationTime(time: NotificationTime) {
        _userProfile.value = _userProfile.value.copy(notificationTime = time)
        autoSaveProgress()
    }

    // === Navigation ===
    fun goToNextCard() {
        val nextCard = getNextCardUseCase(_currentCard.value, _userProfile.value)

        if (nextCard != null) {
            _currentCard.value = nextCard
            _canGoBack.value = true
            updateProgress()
        } else {
            // Onboarding complete
            completeOnboarding()
        }
    }

    fun goToPreviousCard() {
        val prevCard = getPreviousCardUseCase(_currentCard.value, _userProfile.value)

        if (prevCard != null) {
            _currentCard.value = prevCard
            _canGoBack.value = prevCard != CardType.BASIC_INFO
            updateProgress()
        }
    }

    private fun updateProgress() {
        _progress.value = calculateProgressUseCase(_currentCard.value, _userProfile.value)
    }

    private fun autoSaveProgress() {
        viewModelScope.launch {
            saveOnboardingDataUseCase.saveProgress(_userProfile.value)
                .onFailure { e ->
                    // Silently fail for auto-save, but could log
                }
        }
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            _uiState.value = OnboardingUiState.Saving

            saveOnboardingDataUseCase.completeOnboarding(_userProfile.value)
                .onSuccess {
                    _uiState.value = OnboardingUiState.Completed
                    _navigationEvent.value = OnboardingNavigationEvent.NavigateToHome
                }
                .onFailure { error ->
                    _uiState.value = OnboardingUiState.Error(
                        error.message ?: "Failed to save onboarding data"
                    )
                }
        }
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }

    fun retryCompletion() {
        completeOnboarding()
    }
}

sealed class OnboardingUiState {
    object Idle : OnboardingUiState()
    object Saving : OnboardingUiState()
    object Completed : OnboardingUiState()
    data class Error(val message: String) : OnboardingUiState()
}

sealed class OnboardingNavigationEvent {
    object NavigateToHome : OnboardingNavigationEvent()
}
