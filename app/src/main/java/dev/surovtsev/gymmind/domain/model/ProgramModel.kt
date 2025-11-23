package dev.surovtsev.gymmind.domain.model

import dev.surovtsev.gymmind.domain.model.enums.Equipment
import dev.surovtsev.gymmind.domain.model.enums.ProgramDifficulty
import dev.surovtsev.gymmind.domain.model.enums.ProgramGoal
import dev.surovtsev.gymmind.domain.model.enums.ProgramScheduleType
import dev.surovtsev.gymmind.domain.model.enums.ProgramType
import java.time.Instant

/**
 * Program Model - Модель программы тренировок
 */
data class ProgramModel(
    val id: String,
    val name: String,
    val description: String,
    val shortDescription: String? = null,            // Для карточек
    val type: ProgramType,
    val scheduleType: ProgramScheduleType,
    val difficulty: ProgramDifficulty,
    val goals: List<ProgramGoal> = emptyList(),
    val duration: ProgramDuration,
    val requiredEquipment: List<Equipment> = emptyList(),
    val targetAudience: String? = null,              // "Начинающие", "Женщины 30+"
    val prerequisites: List<String> = emptyList(),          // Предварительные требования
    val imageUrl: String? = null,
    val videoPreviewUrl: String? = null,
    val tags: List<String> = emptyList(),
    val structure: ProgramStructure,
    val creatorId: String,
    val originalProgramId: String? = null,           // Если форк
    val isPublic: Boolean = true,
    val isVerified: Boolean = false,
    val isFeatured: Boolean = false,
    val isPremium: Boolean = false,
    val price: Float? = null,
    val currency: String? = null,
    val statistics: ProgramStatistics = ProgramStatistics(),
    val reviews: ProgramReviews = ProgramReviews(),
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
    val publishedAt: Instant? = null
)

data class ProgramDuration(
    val weeks: Int? = null,                          // Для фиксированных программ
    val days: Int? = null,                           // Альтернатива неделям
    val isOngoing: Boolean = false,           // Для циклических программ
    val sessionsPerWeek: Int? = null,
    val minutesPerSession: Int? = null,              // Средняя длительность тренировки
    val totalSessions: Int? = null
)

data class ProgramStructure(
    val phases: List<ProgramPhase> = emptyList(),           // Фазы программы
    val workoutSchedule: WorkoutSchedule,
    val progressionStrategy: ProgressionStrategy,
    val deloadWeeks: List<Int> = emptyList(),               // Недели разгрузки
    val testingDays: List<TestingDay> = emptyList()         // Дни тестирования
)

data class ProgramPhase(
    val id: String,
    val name: String,                         // "Адаптация", "База", "Пик"
    val weekStart: Int,
    val weekEnd: Int,
    val focus: String,                        // "Техника", "Объем", "Интенсивность"
    val description: String? = null,
    val workoutIds: List<String> = emptyList()
)

data class WorkoutSchedule(
    val type: ScheduleType,
    val pattern: SchedulePattern? = null,            // Для циклических
    val fixedSchedule: Map<Int, String> = emptyMap(),     // День -> WorkoutId для фиксированных
    val rules: List<ScheduleRule> = emptyList()             // Правила планирования
)

enum class ScheduleType {
    FIXED,                  // Жесткое расписание
    CYCLIC,                 // Повторяющийся цикл
    FLEXIBLE,               // Гибкое
    ADAPTIVE                // Адаптивное (AI)
}

data class SchedulePattern(
    val cycleDays: Int,                       // Длина цикла
    val workoutDays: List<Int> = emptyList(),               // Дни тренировок в цикле
    val workoutIds: List<String> = emptyList()              // Соответствующие тренировки
)

data class ScheduleRule(
    val type: RuleType,
    val value: String
)

enum class RuleType {
    MIN_REST_DAYS,          // Минимум дней отдыха между тренировками
    MAX_CONSECUTIVE_DAYS,   // Максимум подряд дней тренировок
    MUSCLE_GROUP_REST,      // Отдых мышечной группы
    INTENSITY_WAVE          // Волновая периодизация интенсивности
}

data class ProgressionStrategy(
    val type: ProgressionType,
    val parameters: Map<String, Any> = emptyMap()
)

enum class ProgressionType {
    LINEAR,                 // Линейная прогрессия
    WAVE,                   // Волновая
    BLOCK,                  // Блоковая
    CONJUGATE,              // Сопряженная
    AUTOREGULATED,          // Авторегулируемая
    PERCENTAGE_BASED        // На основе процентов от максимума
}

data class TestingDay(
    val week: Int,
    val day: Int,
    val exercises: List<String> = emptyList(),              // IDs упражнений для тестирования
    val testType: TestType
)

enum class TestType {
    ONE_REP_MAX,
    THREE_REP_MAX,
    AMRAP,
    TIME_TRIAL,
    BENCHMARK
}

data class ProgramStatistics(
    val totalEnrollments: Int = 0,
    val activeUsers: Int = 0,
    val completionRate: Float = 0f,
    val averageProgress: Float = 0f,
    val averageResultImprovement: Float = 0f
)

data class ProgramReviews(
    val averageRating: Float = 0f,
    val totalReviews: Int = 0,
    val fiveStarCount: Int = 0,
    val fourStarCount: Int = 0,
    val threeStarCount: Int = 0,
    val twoStarCount: Int = 0,
    val oneStarCount: Int = 0
)
