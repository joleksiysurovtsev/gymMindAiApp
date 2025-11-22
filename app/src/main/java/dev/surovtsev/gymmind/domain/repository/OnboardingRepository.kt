package dev.surovtsev.gymmind.domain.repository

import dev.surovtsev.gymmind.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing onboarding data persistence
 * Handles both intermediate progress (DataStore) and final completion (Firestore)
 */
interface OnboardingRepository {

    /**
     * Save current onboarding progress locally (DataStore)
     * Called after each card completion to prevent data loss
     *
     * @param userProfile Current user profile with partial onboarding data
     */
    suspend fun saveOnboardingProgress(userProfile: UserProfile): Result<Unit>

    /**
     * Get saved onboarding progress from local storage
     * Used to restore state after app restart
     *
     * @return Flow of UserProfile or null if no progress saved
     */
    fun getOnboardingProgress(): Flow<UserProfile?>

    /**
     * Complete onboarding and save to both Firestore and DataStore
     * Marks hasCompletedOnboarding = true
     *
     * @param userProfile Complete user profile with all onboarding data
     */
    suspend fun completeOnboarding(userProfile: UserProfile): Result<Unit>

    /**
     * Clear local onboarding progress (DataStore)
     * Called after successful Firestore sync or when starting fresh
     */
    suspend fun clearOnboardingProgress(): Result<Unit>
}
