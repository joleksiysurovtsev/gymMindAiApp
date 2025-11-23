package dev.surovtsev.gymmind.domain.model

import dev.surovtsev.gymmind.domain.model.enums.Equipment
import dev.surovtsev.gymmind.domain.model.enums.MuscleGroup
import dev.surovtsev.gymmind.domain.model.enums.WorkoutDifficulty
import dev.surovtsev.gymmind.domain.model.enums.WorkoutIntensity
import dev.surovtsev.gymmind.domain.model.enums.WorkoutPhaseType
import dev.surovtsev.gymmind.domain.model.enums.WorkoutType
import java.time.Instant

/**
 * Workout Model - Модель тренировки
 */
data class WorkoutModel(
    val id: String,
    val name: String,
    val description: String? = null,
    val type: WorkoutType = WorkoutType.STRAIGHT_SETS,
    val difficulty: WorkoutDifficulty = WorkoutDifficulty.MODERATE,
    val estimatedDuration: Int = 30,               // в минутах
    val targetMuscles: List<MuscleGroup> = emptyList(),
    val requiredEquipment: List<Equipment> = emptyList(),
    val phases: List<WorkoutPhase> = emptyList(),
    val imageUrl: String? = null,
    val tags: List<String> = emptyList(),
    val instructions: String? = null,
    val warmupNotes: String? = null,
    val cooldownNotes: String? = null,
    val creatorId: String? = null,
    val programId: String? = null,                   // Если часть программы
    val isPublic: Boolean = true,
    val isTemplate: Boolean = false,          // Шаблон для копирования
    val totalVolume: Float? = null,           // Общий тоннаж
    val estimatedCalories: Int? = null,
    val intensityLevel: WorkoutIntensity = WorkoutIntensity.MODERATE,
    val restBetweenExercises: Int? = null,           // в секундах
    val popularityScore: Float = 0f,
    val completionCount: Int = 0,
    val averageRating: Float = 0f,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

data class WorkoutPhase(
    val id: String,
    val name: String,                         // "Разминка", "Основная часть"
    val type: WorkoutPhaseType,
    val exercises: List<WorkoutExercise> = emptyList(),
    val duration: Int? = null,                       // в минутах
    val notes: String? = null
)

data class WorkoutExercise(
    val id: String,
    val exerciseId: String,
    val orderIndex: Int,                      // Порядок в тренировке
    val sets: List<ExerciseSet> = emptyList(),
    val restAfter: Int? = null,                      // Отдых после упражнения в секундах
    val notes: String? = null,
    val supersetGroupId: String? = null,             // Для группировки суперсетов
    val circuitGroupId: String? = null,              // Для круговых
    val isOptional: Boolean = false,
    val alternatives: List<String> = emptyList()            // IDs альтернативных упражнений
)

data class ExerciseSet(
    val setNumber: Int,
    val targetReps: Int? = null,
    val targetWeight: Float? = null,
    val targetTime: Int? = null,                     // в секундах
    val targetDistance: Float? = null,
    val minReps: Int? = null,                       // Для диапазона
    val maxReps: Int? = null,
    val rpe: Int? = null,                           // Rate of Perceived Exertion (1-10)
    val tempo: String? = null,                      // "3-1-1-0" (эксцентрик-пауза-концентрик-пауза)
    val dropSetWeight: Float? = null,               // Для дроп-сетов
    val isWarmup: Boolean = false,
    val isAmrap: Boolean = false,            // As Many Reps As Possible
    val notes: String? = null
)
