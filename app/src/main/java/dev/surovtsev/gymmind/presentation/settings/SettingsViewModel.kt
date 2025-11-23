package dev.surovtsev.gymmind.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isGuestMode = MutableStateFlow(false)
    val isGuestMode: StateFlow<Boolean> = _isGuestMode

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _isGuestMode.value = userPreferences.isGuestMode.first()

            // Load user email from auth repository if logged in
            if (!_isGuestMode.value) {
                // TODO: Get user profile from repository
                _userEmail.value = "user@example.com" // Mock for now
            }

            // TODO: Load notifications preference from DataStore
            _notificationsEnabled.value = true
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            _notificationsEnabled.value = enabled
            // TODO: Save to DataStore
            Log.d("SettingsViewModel", "Notifications toggled: $enabled")
        }
    }

    fun signOut() {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "User signed out")
            authRepository.signOut()
        }
    }
}
