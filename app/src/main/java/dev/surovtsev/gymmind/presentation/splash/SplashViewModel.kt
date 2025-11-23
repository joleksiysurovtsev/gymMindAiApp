package dev.surovtsev.gymmind.presentation.splash

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState

    private val _navigationEvent = MutableStateFlow<SplashNavigationEvent?>(null)
    val navigationEvent: StateFlow<SplashNavigationEvent?> = _navigationEvent

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            // Показываем splash минимум 2 секунды для красивой анимации
            delay(2000)

            val isGuestMode = userPreferences.isGuestMode.first()
            val isLoggedIn = userPreferences.isLoggedIn.first()
            val hasCompletedOnboarding = userPreferences.hasCompletedOnboarding.first()

            Log.d("SplashViewModel", "checkAuthState: isGuestMode=$isGuestMode, isLoggedIn=$isLoggedIn, hasCompletedOnboarding=$hasCompletedOnboarding")

            when {
                isGuestMode -> {
                    // Гость - переход сразу на main без онбординга
                    Log.d("SplashViewModel", "Guest mode - navigating to main")
                    _navigationEvent.value = SplashNavigationEvent.NavigateToMain
                }
                !isLoggedIn -> {
                    // Показываем кнопки логина и guest
                    Log.d("SplashViewModel", "Showing login/guest options")
                    _uiState.value = SplashUiState.NotAuthenticated
                }
                !hasCompletedOnboarding -> {
                    // Переход на onboarding
                    Log.d("SplashViewModel", "Navigating to onboarding")
                    _navigationEvent.value = SplashNavigationEvent.NavigateToOnboarding
                }
                else -> {
                    // Переход на main
                    Log.d("SplashViewModel", "Navigating to main")
                    _navigationEvent.value = SplashNavigationEvent.NavigateToMain
                }
            }
        }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            Log.d("SplashViewModel", "User selected guest mode")
            userPreferences.setGuestMode(true)
            _navigationEvent.value = SplashNavigationEvent.NavigateToMain
        }
    }

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.value = SplashUiState.Authenticating

            try {
                val credentialManager = CredentialManager.create(context)

                // Authentication -> Sign-in method -> Google -> Web SDK configuration
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("24687499857-q18an5672sf19omd1urlrabmjer3v7il.apps.googleusercontent.com")
                    .setAutoSelectEnabled(false)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                // Extract ID token from credential
                val credential = result.credential
                if (credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

                    Log.d("SplashViewModel", "Google Sign-In successful, ID token obtained")

                    authRepository.signInWithGoogle(idToken)
                        .onSuccess { profile ->
                            Log.d("SplashViewModel", "Sign in successful, user: ${profile.email}, onboarding: ${profile.hasCompletedOnboarding}")

                            // Выход из guest mode при логине
                            userPreferences.setGuestMode(false)

                            // Навигация в зависимости от onboarding статуса
                            if (profile.hasCompletedOnboarding) {
                                _navigationEvent.value = SplashNavigationEvent.NavigateToMain
                            } else {
                                _navigationEvent.value = SplashNavigationEvent.NavigateToOnboarding
                            }
                        }
                        .onFailure { error ->
                            Log.e("SplashViewModel", "Sign in failed", error)
                            _uiState.value = SplashUiState.Error(
                                error.message ?: "Unknown authentication error"
                            )
                            // Вернуть кнопку логина после задержки
                            delay(2000)
                            _uiState.value = SplashUiState.NotAuthenticated
                        }
                } else {
                    Log.e("SplashViewModel", "Unexpected credential type: ${credential.type}")
                    _uiState.value = SplashUiState.Error("Invalid credential type")
                    delay(2000)
                    _uiState.value = SplashUiState.NotAuthenticated
                }
            } catch (e: GetCredentialCancellationException) {
                Log.d("SplashViewModel", "User cancelled Google Sign-In")
                _uiState.value = SplashUiState.NotAuthenticated
            } catch (e: NoCredentialException) {
                Log.e("SplashViewModel", "No Google credentials available", e)
                _uiState.value = SplashUiState.Error(
                    "No Google account found. Please check your internet connection."
                )
                delay(2000)
                _uiState.value = SplashUiState.NotAuthenticated
            } catch (e: GetCredentialException) {
                Log.e("SplashViewModel", "Google Sign-In failed", e)
                _uiState.value = SplashUiState.Error(
                    "Sign-in failed. Please check your internet connection and try again."
                )
                delay(2000)
                _uiState.value = SplashUiState.NotAuthenticated
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Unexpected error during Google Sign-In", e)
                _uiState.value = SplashUiState.Error(
                    "Unexpected error. Please try again."
                )
                delay(2000)
                _uiState.value = SplashUiState.NotAuthenticated
            }
        }
    }
}

sealed class SplashUiState {
    object Loading : SplashUiState()
    object NotAuthenticated : SplashUiState()
    object Authenticating : SplashUiState()
    data class Error(val message: String) : SplashUiState()
}

sealed class SplashNavigationEvent {
    object NavigateToOnboarding : SplashNavigationEvent()
    object NavigateToMain : SplashNavigationEvent()
}
