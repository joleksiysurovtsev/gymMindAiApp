package dev.surovtsev.gymmind.data.local.entity

import androidx.room.*
import java.time.Instant

/**
 * Program Entity для Room Database
 */
@Entity(
    tableName = "programs",
    indices = [
        Index(value = ["name"]),
        Index(value = ["type"]),
        Index(value = ["creatorId"]),
        Index(value = ["isPublic", "isVerified", "isFeatured"]),
        Index(value = ["createdAt"]),
        Index(value = ["originalProgramId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["creatorId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["originalProgramId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class ProgramEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val shortDescription: String?,
    val type: String,
    val scheduleType: String,
    val difficulty: String,
    val goals: String,                        // JSON array
    val duration: String,                     // JSON object
    val requiredEquipment: String,            // JSON array
    val targetAudience: String?,
    val prerequisites: String?,               // JSON array
    val imageUrl: String?,
    val videoPreviewUrl: String?,
    val tags: String?,                        // JSON array
    val structure: String,                    // JSON object
    val creatorId: String,
    val originalProgramId: String?,
    val isPublic: Boolean = true,
    val isVerified: Boolean = false,
    val isFeatured: Boolean = false,
    val isPremium: Boolean = false,
    val price: Float?,
    val currency: String?,
    val statistics: String,                   // JSON object
    val reviews: String,                      // JSON object
    val createdAt: Instant,
    val updatedAt: Instant,
    val publishedAt: Instant?,

    // Sync
    @ColumnInfo(index = true)
    val isSynced: Boolean = false,
    val syncVersion: Int = 0
)

/**
 * Junction table для связи программ и тренировок
 */
@Entity(
    tableName = "program_workouts",
    primaryKeys = ["programId", "workoutId", "scheduleDay"],
    indices = [
        Index(value = ["programId"]),
        Index(value = ["workoutId"]),
        Index(value = ["scheduleDay"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProgramWorkoutCrossRef(
    val programId: String,
    val workoutId: String,
    val scheduleDay: Int,                     // День в программе (1-based)
    val weekNumber: Int?,                     // Номер недели
    val phaseId: String?,                     // ID фазы программы
    val isOptional: Boolean = false,
    val alternatives: String?,                // JSON array
    val notes: String?,
    val createdAt: Instant = Instant.now()
)

/**
 * Junction table для участия пользователей в программах
 */
@Entity(
    tableName = "user_program_enrollments",
    primaryKeys = ["userId", "programId"],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["programId"]),
        Index(value = ["startedAt"]),
        Index(value = ["status"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserProgramEnrollment(
    val userId: String,
    val programId: String,
    val status: String,                       // ACTIVE, PAUSED, COMPLETED, ABANDONED
    val startedAt: Instant,
    val pausedAt: Instant?,
    val completedAt: Instant?,
    val currentDay: Int = 1,
    val currentWeek: Int = 1,
    val progressPercentage: Float = 0f,
    val completedWorkouts: String,            // JSON array of workout IDs
    val skippedWorkouts: String?,             // JSON array
    val modifications: String?,               // JSON object с изменениями
    val notes: String?,
    val rating: Float?,
    val review: String?,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)
