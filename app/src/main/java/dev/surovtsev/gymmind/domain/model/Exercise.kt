package dev.surovtsev.gymmind.domain.model

import dev.surovtsev.gymmind.domain.model.enums.Equipment
import dev.surovtsev.gymmind.domain.model.enums.MuscleGroup

/**
 * Simplified exercise model for workouts
 * For full exercise details, use ExerciseModel
 */
data class Exercise(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val sets: Int = 3,                      // Number of sets
    val reps: String = "10-12",             // Repetitions (can be range like "10-12" or duration like "30s")
    val restSeconds: Int = 60,              // Rest time between sets in seconds
    val equipment: List<Equipment> = emptyList(),  // Required equipment
    val muscleGroups: List<MuscleGroup> = emptyList(),  // Target muscle groups
    val videoUrl: String? = null,           // Optional video demonstration URL
    val imageUrl: String? = null,           // Optional image URL
    val instructions: List<String> = emptyList()   // Step-by-step instructions
)
