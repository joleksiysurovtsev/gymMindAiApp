package dev.surovtsev.gymmind.domain.model

/**
 * Gender options for user profile
 */
enum class Gender {
    MALE,
    FEMALE,
    OTHER,
    PREFER_NOT_TO_SAY
}

/**
 * Main fitness goals that determine training program recommendations
 * and conditional onboarding flow
 */
enum class FitnessGoal {
    LOSE_WEIGHT,            // Show target weight card
    GAIN_MUSCLE,            // Show target weight card
    IMPROVE_FLEXIBILITY,    // Skip equipment selection
    IMPROVE_ENDURANCE,
    MAINTAIN_FITNESS
}

/**
 * User's fitness experience level
 * Determines whether to show basic skills assessment
 */
enum class ExperienceLevel {
    BEGINNER,       // Show basic skills check
    INTERMEDIATE,   // Skip basic skills
    ADVANCED        // Skip basic skills
}

/**
 * Workout location that affects equipment availability
 */
enum class WorkoutLocation {
    HOME_NO_EQUIPMENT,      // Skip equipment selection
    HOME_WITH_EQUIPMENT,    // Show equipment selection
    GYM,                    // Show equipment selection
    OUTDOOR                 // Skip equipment selection
}

/**
 * Available equipment for training
 * Used for workout program personalization
 */
enum class Equipment {
    DUMBBELLS,
    BARBELL,
    PULL_UP_BAR,
    RESISTANCE_BANDS,
    BENCH,
    MACHINES,
    CARDIO_MACHINES
}

/**
 * Frequency of workouts per week
 */
enum class DaysPerWeek {
    TWO_THREE,      // 2-3 days per week
    FOUR_FIVE,      // 4-5 days per week
    SIX_SEVEN       // 6-7 days per week
}

/**
 * Duration of single workout session
 */
enum class MinutesPerWorkout {
    SHORT,      // 15-30 minutes
    MEDIUM,     // 30-45 minutes
    LONG        // 45-60+ minutes
}

/**
 * Health limitations and contraindications
 * Used for exercise safety and program adaptation
 */
enum class HealthLimitation {
    NONE,
    BACK_ISSUES,
    KNEE_ISSUES,
    SHOULDER_ISSUES,
    CARDIOVASCULAR,
    PREGNANCY,
    OTHER               // Requires custom text description
}

/**
 * Preferred time for workout reminders
 */
enum class NotificationTime {
    MORNING,        // 6:00-10:00
    AFTERNOON,      // 12:00-16:00
    EVENING,        // 18:00-21:00
    ANYTIME,        // No preference
    NONE            // Disable notifications
}
