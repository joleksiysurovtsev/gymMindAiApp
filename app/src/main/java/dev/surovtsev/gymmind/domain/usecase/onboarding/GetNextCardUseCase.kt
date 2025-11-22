package dev.surovtsev.gymmind.domain.usecase.onboarding

import dev.surovtsev.gymmind.domain.model.*
import javax.inject.Inject

/**
 * Use case that determines the next onboarding card based on current card and user's data
 * Implements conditional branching logic for dynamic onboarding flow
 */
class GetNextCardUseCase @Inject constructor() {

    /**
     * Returns the next card to show, or null if onboarding is complete
     *
     * @param currentCard Current card being displayed
     * @param userProfile Current user profile with filled data
     * @return Next CardType or null if onboarding flow is complete
     */
    operator fun invoke(currentCard: CardType, userProfile: UserProfile): CardType? {
        return when (currentCard) {
            CardType.BASIC_INFO -> {
                // Always go to goal selection
                CardType.GOAL
            }

            CardType.GOAL -> {
                // If goal requires target weight, show target weight card
                when (userProfile.goal) {
                    FitnessGoal.LOSE_WEIGHT,
                    FitnessGoal.GAIN_MUSCLE -> CardType.TARGET_WEIGHT

                    FitnessGoal.IMPROVE_FLEXIBILITY -> {
                        // Skip experience and location for flexibility goal
                        CardType.SCHEDULE
                    }

                    else -> CardType.EXPERIENCE
                }
            }

            CardType.TARGET_WEIGHT -> {
                // After target weight, always go to experience
                CardType.EXPERIENCE
            }

            CardType.EXPERIENCE -> {
                // If flexibility goal, skip location
                if (userProfile.goal == FitnessGoal.IMPROVE_FLEXIBILITY) {
                    CardType.SCHEDULE
                } else {
                    CardType.LOCATION
                }
            }

            CardType.LOCATION -> {
                // If location has equipment, show equipment selection
                when (userProfile.location) {
                    WorkoutLocation.HOME_WITH_EQUIPMENT,
                    WorkoutLocation.GYM -> CardType.EQUIPMENT

                    else -> CardType.SCHEDULE
                }
            }

            CardType.EQUIPMENT -> {
                // After equipment, always go to schedule
                CardType.SCHEDULE
            }

            CardType.SCHEDULE -> {
                // Always go to limitations
                CardType.LIMITATIONS
            }

            CardType.LIMITATIONS -> {
                // If beginner, show basic skills check
                when (userProfile.experienceLevel) {
                    ExperienceLevel.BEGINNER -> CardType.BASIC_SKILLS
                    else -> CardType.NOTIFICATIONS
                }
            }

            CardType.BASIC_SKILLS -> {
                // After basic skills, always go to notifications
                CardType.NOTIFICATIONS
            }

            CardType.NOTIFICATIONS -> {
                // Onboarding complete
                null
            }
        }
    }
}
