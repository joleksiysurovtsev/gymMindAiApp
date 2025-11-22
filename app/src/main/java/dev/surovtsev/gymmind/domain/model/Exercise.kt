package dev.surovtsev.gymmind.domain.model

/**
 * Represents a single exercise in a workout
 */
data class Exercise(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val sets: Int = 3,                      // Number of sets
    val reps: String = "10-12",             // Repetitions (can be range like "10-12" or duration like "30s")
    val restSeconds: Int = 60,              // Rest time between sets in seconds
    val equipment: List<Equipment> = emptyList(),  // Required equipment
    val muscleGroups: List<String> = emptyList(),  // Target muscle groups
    val videoUrl: String? = null,           // Optional video demonstration URL
    val imageUrl: String? = null,           // Optional image URL
    val instructions: List<String> = emptyList()   // Step-by-step instructions
)
