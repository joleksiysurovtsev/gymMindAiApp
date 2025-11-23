package dev.surovtsev.gymmind.domain.model

import dev.surovtsev.gymmind.domain.model.enums.Equipment
import dev.surovtsev.gymmind.domain.model.enums.ExerciseDifficulty
import dev.surovtsev.gymmind.domain.model.enums.ExerciseForce
import dev.surovtsev.gymmind.domain.model.enums.ExerciseMechanic
import dev.surovtsev.gymmind.domain.model.enums.ExerciseType
import dev.surovtsev.gymmind.domain.model.enums.HeartRateZone
import dev.surovtsev.gymmind.domain.model.enums.MuscleGroup
import java.time.Instant

/**
 * Exercise Model - Модель упражнения
 */
data class ExerciseModel(
    val id: String,
    val name: String,
    val description: String? = null,
    val instructions: List<String> = emptyList(),           // Пошаговые инструкции
    val tips: List<String> = emptyList(),                   // Советы по выполнению
    val warnings: List<String> = emptyList(),               // Предупреждения
    val type: ExerciseType,
    val primaryMuscles: List<MuscleGroup> = emptyList(),
    val secondaryMuscles: List<MuscleGroup> = emptyList(),
    val equipment: List<Equipment> = emptyList(),
    val force: ExerciseForce? = null,
    val mechanic: ExerciseMechanic? = null,
    val difficulty: ExerciseDifficulty = ExerciseDifficulty.BEGINNER,
    val mediaAssets: ExerciseMedia = ExerciseMedia(),
    val metrics: ExerciseMetrics = ExerciseMetrics(),
    val tags: List<String> = emptyList(),
    val isBodyweight: Boolean = false,
    val isCompound: Boolean = false,
    val isCardio: Boolean = false,
    val alternatives: List<String> = emptyList(),            // IDs альтернативных упражнений
    val progressions: List<String> = emptyList(),           // IDs упражнений-прогрессий
    val regressions: List<String> = emptyList(),            // IDs упражнений-регрессий
    val creatorId: String? = null,                   // Null для системных упражнений
    val isPublic: Boolean = true,
    val isVerified: Boolean = false,          // Проверено тренерами
    val popularityScore: Float = 0f,
    val averageRating: Float = 0f,
    val ratingsCount: Int = 0,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

data class ExerciseMedia(
    val thumbnailUrl: String? = null,
    val imageUrls: List<String> = emptyList(),
    val videoUrl: String? = null,
    val animationUrl: String? = null,                // GIF или анимация
    val muscleMapUrl: String? = null,                // Изображение работающих мышц
    val audioGuideUrl: String? = null                // Аудио-инструкция
)

data class ExerciseMetrics(
    val defaultSets: Int? = null,
    val defaultReps: Int? = null,
    val defaultWeight: Float? = null,
    val defaultDuration: Int? = null,         // в секундах
    val defaultDistance: Float? = null,
    val defaultRestTime: Int? = null,         // в секундах
    val caloriesPerMinute: Float? = null,
    val metValue: Float? = null,              // Metabolic Equivalent of Task
    val targetHeartRateZone: HeartRateZone? = null
)
