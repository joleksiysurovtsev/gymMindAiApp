package dev.surovtsev.gymmind.domain.usecase.onboarding

import dev.surovtsev.gymmind.domain.model.*
import javax.inject.Inject

/**
 * Use case that calculates dynamic progress based on user's answers
 * Accounts for conditional cards that may be skipped
 */
class CalculateProgressUseCase @Inject constructor() {

    /**
     * Calculates total number of steps user will complete based on their answers
     *
     * @param userProfile Current user profile with filled data
     * @return Total number of steps (7-10 depending on answers)
     */
    fun getTotalSteps(userProfile: UserProfile): Int {
        var steps = 6 // Base cards: BASIC_INFO, GOAL, EXPERIENCE, SCHEDULE, LIMITATIONS, NOTIFICATIONS

        // Add TARGET_WEIGHT if goal requires it
        if (userProfile.goal in listOf(FitnessGoal.LOSE_WEIGHT, FitnessGoal.GAIN_MUSCLE)) {
            steps++
        }

        // Add LOCATION and possibly EQUIPMENT if not flexibility goal
        if (userProfile.goal != FitnessGoal.IMPROVE_FLEXIBILITY) {
            steps++ // LOCATION

            // Add EQUIPMENT if location supports it
            if (userProfile.location in listOf(WorkoutLocation.HOME_WITH_EQUIPMENT, WorkoutLocation.GYM)) {
                steps++
            }
        }

        // Add BASIC_SKILLS if beginner
        if (userProfile.experienceLevel == ExperienceLevel.BEGINNER) {
            steps++
        }

        return steps
    }

    /**
     * Calculates current step number in the flow
     *
     * @param currentCard Card being displayed
     * @param userProfile Current user profile with filled data
     * @return Current step number (1-based index)
     */
    fun getCurrentStep(currentCard: CardType, userProfile: UserProfile): Int {
        var step = 1

        // Count steps based on the order of cards
        if (currentCard == CardType.BASIC_INFO) return 1

        step++ // GOAL
        if (currentCard == CardType.GOAL) return step

        // TARGET_WEIGHT (conditional)
        if (userProfile.goal in listOf(FitnessGoal.LOSE_WEIGHT, FitnessGoal.GAIN_MUSCLE)) {
            step++
            if (currentCard == CardType.TARGET_WEIGHT) return step
        }

        // EXPERIENCE (skip if flexibility)
        if (userProfile.goal != FitnessGoal.IMPROVE_FLEXIBILITY) {
            step++
            if (currentCard == CardType.EXPERIENCE) return step

            // LOCATION
            step++
            if (currentCard == CardType.LOCATION) return step

            // EQUIPMENT (conditional)
            if (userProfile.location in listOf(WorkoutLocation.HOME_WITH_EQUIPMENT, WorkoutLocation.GYM)) {
                step++
                if (currentCard == CardType.EQUIPMENT) return step
            }
        }

        // SCHEDULE
        step++
        if (currentCard == CardType.SCHEDULE) return step

        // LIMITATIONS
        step++
        if (currentCard == CardType.LIMITATIONS) return step

        // BASIC_SKILLS (conditional)
        if (userProfile.experienceLevel == ExperienceLevel.BEGINNER) {
            step++
            if (currentCard == CardType.BASIC_SKILLS) return step
        }

        // NOTIFICATIONS
        step++
        return step
    }

    /**
     * Calculates progress as a fraction (0.0 to 1.0)
     *
     * @param currentCard Card being displayed
     * @param userProfile Current user profile with filled data
     * @return Progress from 0.0 (start) to 1.0 (complete)
     */
    operator fun invoke(currentCard: CardType, userProfile: UserProfile): Float {
        val currentStep = getCurrentStep(currentCard, userProfile)
        val totalSteps = getTotalSteps(userProfile)
        return currentStep.toFloat() / totalSteps.toFloat()
    }
}
