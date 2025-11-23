package dev.surovtsev.gymmind.data.local.entity

import androidx.room.*
import java.time.Instant

/**
 * Exercise Entity для Room Database
 */
@Entity(
    tableName = "exercises",
    indices = [
        Index(value = ["name"]),
        Index(value = ["type"]),
        Index(value = ["creatorId"]),
        Index(value = ["isPublic", "isVerified"])
    ]
)
data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val instructions: String,                 // JSON array
    val tips: String?,                        // JSON array
    val warnings: String?,                     // JSON array
    val type: String,
    val primaryMuscles: String,               // JSON array
    val secondaryMuscles: String?,            // JSON array
    val equipment: String,                    // JSON array
    val force: String?,
    val mechanic: String?,
    val difficulty: String,
    val mediaAssets: String,                  // JSON object
    val metrics: String,                      // JSON object
    val tags: String?,                        // JSON array
    val isBodyweight: Boolean,
    val isCompound: Boolean,
    val isCardio: Boolean,
    val alternatives: String?,                // JSON array of IDs
    val progressions: String?,                // JSON array of IDs
    val regressions: String?,                 // JSON array of IDs
    val creatorId: String?,
    val isPublic: Boolean = true,
    val isVerified: Boolean = false,
    @ColumnInfo(index = true)
    val popularityScore: Float = 0f,
    val averageRating: Float = 0f,
    val ratingsCount: Int = 0,
    val createdAt: Instant,
    val updatedAt: Instant,

    // Sync
    @ColumnInfo(index = true)
    val isSynced: Boolean = false,
    val syncVersion: Int = 0
)

/**
 * FTS4 таблица для полнотекстового поиска упражнений
 */
@Entity(tableName = "exercise_fts")
@Fts4(contentEntity = ExerciseEntity::class)
data class ExerciseFts(
    val name: String,
    val description: String?,
    val tags: String?
)
