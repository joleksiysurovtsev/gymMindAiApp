package dev.surovtsev.gymmind.domain.model

/**
 * Represents a complete workout plan
 */
data class WorkoutPlan(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val category: WorkoutCategory = WorkoutCategory.FREE_BASIC,
    val difficulty: ExperienceLevel = ExperienceLevel.BEGINNER,
    val durationMinutes: Int = 30,
    val exercises: List<Exercise> = emptyList(),
    val caloriesBurnEstimate: Int = 200,    // Estimated calories burned
    val imageUrl: String? = null,           // Workout thumbnail
    val tags: List<String> = emptyList()    // Tags like "Full Body", "Cardio", "Strength"
)
