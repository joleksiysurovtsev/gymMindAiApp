package dev.surovtsev.gymmind.domain.model

/**
 * Category of workout accessibility
 */
enum class WorkoutCategory {
    /**
     * Free workouts available to all users including guests
     */
    FREE_BASIC,

    /**
     * Premium workouts available only to authenticated users
     */
    PREMIUM
}
