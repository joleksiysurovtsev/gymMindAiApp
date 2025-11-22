package dev.surovtsev.gymmind.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val authRepository: AuthRepository
) : ViewModel() {

    val isGuestMode: StateFlow<Boolean> = userPreferences.isGuestMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isLoggedIn: StateFlow<Boolean> = userPreferences.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val currentUser: StateFlow<dev.surovtsev.gymmind.domain.model.UserProfile?> =
        authRepository.getCurrentUser()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _navigationEvent = MutableStateFlow<ProfileNavigationEvent?>(null)
    val navigationEvent: StateFlow<ProfileNavigationEvent?> = _navigationEvent.asStateFlow()

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            // Clear all preferences including guest mode
            userPreferences.clear()
            _navigationEvent.value = ProfileNavigationEvent.NavigateToSplash
        }
    }

    fun exitGuestModeAndSignIn() {
        viewModelScope.launch {
            // Clear guest mode data
            userPreferences.clear()
            _navigationEvent.value = ProfileNavigationEvent.NavigateToSplash
        }
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
}

sealed class ProfileNavigationEvent {
    object NavigateToSplash : ProfileNavigationEvent()
}
