package dev.surovtsev.gymmind.domain.usecase.onboarding

import dev.surovtsev.gymmind.domain.model.*
import javax.inject.Inject

/**
 * Use case that determines the previous onboarding card based on current card and user's data
 * Implements reverse conditional branching logic to correctly handle back navigation
 */
class GetPreviousCardUseCase @Inject constructor() {

    /**
     * Returns the previous card to navigate back to, or null if at the start
     *
     * @param currentCard Current card being displayed
     * @param userProfile Current user profile with filled data
     * @return Previous CardType or null if at the beginning of onboarding
     */
    operator fun invoke(currentCard: CardType, userProfile: UserProfile): CardType? {
        return when (currentCard) {
            CardType.BASIC_INFO -> {
                // First card, cannot go back
                null
            }

            CardType.GOAL -> {
                // Always came from basic info
                CardType.BASIC_INFO
            }

            CardType.TARGET_WEIGHT -> {
                // Always came from goal
                CardType.GOAL
            }

            CardType.EXPERIENCE -> {
                // Came from target weight or goal (depending on goal type)
                when (userProfile.goal) {
                    FitnessGoal.LOSE_WEIGHT,
                    FitnessGoal.GAIN_MUSCLE -> CardType.TARGET_WEIGHT

                    else -> CardType.GOAL
                }
            }

            CardType.LOCATION -> {
                // Always came from experience
                CardType.EXPERIENCE
            }

            CardType.EQUIPMENT -> {
                // Always came from location
                CardType.LOCATION
            }

            CardType.SCHEDULE -> {
                // Could come from: equipment, location, experience, or goal (flexibility)
                when {
                    userProfile.goal == FitnessGoal.IMPROVE_FLEXIBILITY -> {
                        // Flexibility path skips experience and location
                        CardType.GOAL
                    }

                    userProfile.location in listOf(
                        WorkoutLocation.HOME_WITH_EQUIPMENT,
                        WorkoutLocation.GYM
                    ) -> {
                        // Has equipment selection
                        CardType.EQUIPMENT
                    }

                    userProfile.location != null -> {
                        // Has location but no equipment
                        CardType.LOCATION
                    }

                    else -> {
                        // Fallback to experience
                        CardType.EXPERIENCE
                    }
                }
            }

            CardType.LIMITATIONS -> {
                // Always came from schedule
                CardType.SCHEDULE
            }

            CardType.BASIC_SKILLS -> {
                // Always came from limitations
                CardType.LIMITATIONS
            }

            CardType.NOTIFICATIONS -> {
                // Could come from limitations (intermediate/advanced) or basic skills (beginner)
                when (userProfile.experienceLevel) {
                    ExperienceLevel.BEGINNER -> CardType.BASIC_SKILLS
                    else -> CardType.LIMITATIONS
                }
            }
        }
    }
}
