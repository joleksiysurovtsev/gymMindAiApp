package dev.surovtsev.gymmind.domain.model

import dev.surovtsev.gymmind.domain.model.enums.ActivityLevel
import dev.surovtsev.gymmind.domain.model.enums.DistanceUnit
import dev.surovtsev.gymmind.domain.model.enums.Gender
import dev.surovtsev.gymmind.domain.model.enums.HeightUnit
import dev.surovtsev.gymmind.domain.model.enums.ProgramGoal
import dev.surovtsev.gymmind.domain.model.enums.PrivacyLevel
import dev.surovtsev.gymmind.domain.model.enums.TemperatureUnit
import dev.surovtsev.gymmind.domain.model.enums.WeightUnit
import java.time.Instant
import java.time.LocalDate

/**
 * User Model - Модель пользователя
 */
data class User(
    val id: String,
    val username: String,
    val email: String,
    val fullName: String? = null,
    val avatarUrl: String? = null,
    val bio: String? = null,
    val dateOfBirth: LocalDate? = null,
    val gender: Gender? = null,
    val height: Float? = null,                       // в сантиметрах
    val weight: Float? = null,                       // в килограммах
    val activityLevel: ActivityLevel? = null,
    val fitnessGoals: List<ProgramGoal> = emptyList(),
    val preferredUnits: UnitsPreference = UnitsPreference(),
    val timezone: String = "UTC",
    val isPremium: Boolean = false,
    val isVerified: Boolean = false,
    val privacySettings: PrivacySettings = PrivacySettings(),
    val notificationSettings: NotificationSettings = NotificationSettings(),
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
    val lastActiveAt: Instant? = null,

    // Статистика
    val totalWorkouts: Int = 0,
    val totalDuration: Long = 0,              // в минутах
    val totalCalories: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val level: Int = 1,
    val experiencePoints: Int = 0,

    // Социальные метрики
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val programsCreatedCount: Int = 0,
    val averageRating: Float = 0f
)

data class UnitsPreference(
    val weight: WeightUnit = WeightUnit.KG,
    val distance: DistanceUnit = DistanceUnit.KILOMETERS,
    val height: HeightUnit = HeightUnit.CM,
    val temperature: TemperatureUnit = TemperatureUnit.CELSIUS
)

data class PrivacySettings(
    val profileVisibility: PrivacyLevel = PrivacyLevel.PUBLIC,
    val workoutVisibility: PrivacyLevel = PrivacyLevel.FRIENDS_ONLY,
    val statsVisibility: PrivacyLevel = PrivacyLevel.FRIENDS_ONLY,
    val allowFollowRequests: Boolean = true,
    val showOnLeaderboards: Boolean = true,
    val shareLocationData: Boolean = false
)

data class NotificationSettings(
    val workoutReminders: Boolean = true,
    val achievementAlerts: Boolean = true,
    val socialNotifications: Boolean = true,
    val challengeUpdates: Boolean = true,
    val weeklyReports: Boolean = true,
    val motivationalMessages: Boolean = false
)
