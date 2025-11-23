package dev.surovtsev.gymmind.data.local.entity

import androidx.room.*
import java.time.Instant
import java.time.LocalDate

/**
 * User Entity для Room Database
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(index = true)
    val username: String,
    @ColumnInfo(index = true)
    val email: String,
    val fullName: String?,
    val avatarUrl: String?,
    val bio: String?,
    val dateOfBirth: LocalDate?,
    val gender: String?,                      // Enum stored as String
    val height: Float?,
    val weight: Float?,
    val activityLevel: String?,
    val fitnessGoals: String,                 // JSON array
    val preferredUnits: String,               // JSON object
    val timezone: String,
    val isPremium: Boolean = false,
    val isVerified: Boolean = false,
    val privacySettings: String,              // JSON
    val notificationSettings: String,         // JSON
    @ColumnInfo(index = true)
    val createdAt: Instant,
    val updatedAt: Instant,
    val lastActiveAt: Instant?,

    // Денормализованная статистика для быстрого доступа
    val totalWorkouts: Int = 0,
    val totalDuration: Long = 0,
    val totalCalories: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val level: Int = 1,
    val experiencePoints: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val programsCreatedCount: Int = 0,
    val averageRating: Float = 0f,

    // Sync fields
    @ColumnInfo(index = true)
    val isSynced: Boolean = false,
    val lastSyncAt: Instant? = null
)
