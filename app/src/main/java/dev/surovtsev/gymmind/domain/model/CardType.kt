package dev.surovtsev.gymmind.domain.model

/**
 * Types of onboarding cards in the questionnaire flow
 * Order and visibility depend on user's previous answers
 */
enum class CardType {
    /**
     * Step 1: Basic user information
     * Fields: gender, age, height, currentWeight
     * Always shown
     */
    BASIC_INFO,

    /**
     * Step 2: Main fitness goal selection
     * Determines which cards to show next
     * Always shown
     */
    GOAL,

    /**
     * Step 3: Target weight (conditional)
     * Shown only if goal is LOSE_WEIGHT or GAIN_MUSCLE
     */
    TARGET_WEIGHT,

    /**
     * Step 4: Fitness experience level
     * Determines if basic skills check is needed
     * Always shown
     */
    EXPERIENCE,

    /**
     * Step 5: Workout location
     * Determines if equipment selection is needed
     * Skipped if goal is IMPROVE_FLEXIBILITY
     */
    LOCATION,

    /**
     * Step 6: Available equipment (conditional)
     * Shown only if location is HOME_WITH_EQUIPMENT or GYM
     */
    EQUIPMENT,

    /**
     * Step 7: Workout schedule
     * Fields: daysPerWeek, minutesPerWorkout
     * Always shown
     */
    SCHEDULE,

    /**
     * Step 8: Health limitations and contraindications
     * Always shown
     */
    LIMITATIONS,

    /**
     * Step 9: Basic skills assessment (conditional)
     * Shown only if experience level is BEGINNER
     */
    BASIC_SKILLS,

    /**
     * Step 10: Notification preferences
     * Final card, always shown
     */
    NOTIFICATIONS
}
