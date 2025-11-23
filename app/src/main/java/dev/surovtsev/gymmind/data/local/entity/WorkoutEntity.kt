package dev.surovtsev.gymmind.data.local.entity

import androidx.room.*
import java.time.Instant

/**
 * Workout Entity для Room Database
 */
@Entity(
    tableName = "workouts",
    indices = [
        Index(value = ["name"]),
        Index(value = ["programId"]),
        Index(value = ["creatorId"]),
        Index(value = ["createdAt"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["creatorId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class WorkoutEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val type: String,
    val difficulty: String,
    val estimatedDuration: Int,
    val targetMuscles: String,                // JSON array
    val requiredEquipment: String,            // JSON array
    val phases: String,                       // JSON array
    val imageUrl: String?,
    val tags: String?,                        // JSON array
    val instructions: String?,
    val warmupNotes: String?,
    val cooldownNotes: String?,
    val creatorId: String?,
    val programId: String?,
    val isPublic: Boolean = true,
    val isTemplate: Boolean = false,
    val totalVolume: Float?,
    val estimatedCalories: Int?,
    val intensityLevel: String,
    val restBetweenExercises: Int?,
    @ColumnInfo(index = true)
    val popularityScore: Float = 0f,
    val completionCount: Int = 0,
    val averageRating: Float = 0f,
    val createdAt: Instant,
    val updatedAt: Instant,

    // Sync
    @ColumnInfo(index = true)
    val isSynced: Boolean = false,
    val syncVersion: Int = 0
)

/**
 * Junction table для связи тренировок и упражнений
 */
@Entity(
    tableName = "workout_exercises",
    primaryKeys = ["workoutId", "exerciseId", "orderIndex"],
    indices = [
        Index(value = ["workoutId"]),
        Index(value = ["exerciseId"]),
        Index(value = ["orderIndex"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutExerciseCrossRef(
    val workoutId: String,
    val exerciseId: String,
    val orderIndex: Int,
    val sets: String,                         // JSON array of sets
    val restAfter: Int?,
    val notes: String?,
    val supersetGroupId: String?,
    val circuitGroupId: String?,
    val isOptional: Boolean = false,
    val alternatives: String?,                // JSON array of exercise IDs
    val createdAt: Instant = Instant.now()
)
