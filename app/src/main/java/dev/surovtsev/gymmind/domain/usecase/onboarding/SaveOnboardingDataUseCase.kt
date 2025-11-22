package dev.surovtsev.gymmind.domain.usecase.onboarding

import dev.surovtsev.gymmind.domain.model.UserProfile
import dev.surovtsev.gymmind.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * Use case for saving onboarding data
 * Handles both intermediate progress saves and final completion
 */
class SaveOnboardingDataUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    /**
     * Save intermediate progress locally (DataStore)
     * Called after each card completion to prevent data loss
     *
     * @param userProfile Current user profile with partial data
     */
    suspend fun saveProgress(userProfile: UserProfile): Result<Unit> {
        return onboardingRepository.saveOnboardingProgress(userProfile)
    }

    /**
     * Complete onboarding and sync to Firestore
     * Marks hasCompletedOnboarding = true
     *
     * @param userProfile Complete user profile with all onboarding data
     */
    suspend fun completeOnboarding(userProfile: UserProfile): Result<Unit> {
        return onboardingRepository.completeOnboarding(userProfile)
    }
}
