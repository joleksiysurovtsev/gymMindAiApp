package dev.surovtsev.gymmind.domain.model

import dev.surovtsev.gymmind.domain.model.enums.ProgramDifficulty

/**
 * Simplified workout plan model
 * For full workout details, use WorkoutModel
 */
data class WorkoutPlan(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val category: WorkoutCategory = WorkoutCategory.FREE_BASIC,
    val difficulty: ProgramDifficulty = ProgramDifficulty.BEGINNER,
    val durationMinutes: Int = 30,
    val exercises: List<Exercise> = emptyList(),
    val caloriesBurnEstimate: Int = 200,    // Estimated calories burned
    val imageUrl: String? = null,           // Workout thumbnail
    val tags: List<String> = emptyList()    // Tags like "Full Body", "Cardio", "Strength"
)
