package dev.surovtsev.gymmind.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = stringPreferencesKey("user_id")
        private val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
        private val ONBOARDING_PROGRESS = stringPreferencesKey("onboarding_progress")
        private val IS_GUEST_MODE = booleanPreferencesKey("is_guest_mode")
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { it[IS_LOGGED_IN] ?: false }
    val userId: Flow<String?> = dataStore.data.map { it[USER_ID] }
    val hasCompletedOnboarding: Flow<Boolean> = dataStore.data.map { it[HAS_COMPLETED_ONBOARDING] ?: false }
    val isGuestMode: Flow<Boolean> = dataStore.data.map { it[IS_GUEST_MODE] ?: false }

    suspend fun setLoggedIn(isLoggedIn: Boolean, userId: String? = null) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
            if (userId != null) {
                preferences[USER_ID] = userId
            }
        }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_COMPLETED_ONBOARDING] = completed
        }
    }

    suspend fun setOnboardingProgress(progressJson: String) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_PROGRESS] = progressJson
        }
    }

    fun getOnboardingProgress(): Flow<String?> {
        return dataStore.data.map { it[ONBOARDING_PROGRESS] }
    }

    suspend fun clearOnboardingProgress() {
        dataStore.edit { preferences ->
            preferences.remove(ONBOARDING_PROGRESS)
        }
    }

    suspend fun setGuestMode(isGuest: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_GUEST_MODE] = isGuest
            if (isGuest) {
                // When entering guest mode, ensure user is not logged in
                preferences[IS_LOGGED_IN] = false
                preferences[HAS_COMPLETED_ONBOARDING] = false
            }
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
