package dev.surovtsev.gymmind.domain.model.enums

/**
 * Workout & Training Enums - Тренировочные перечисления
 */
enum class WorkoutType {
    STRAIGHT_SETS,         // Обычные подходы
    SUPERSET,              // Суперсеты
    GIANT_SET,             // Гигантские сеты
    CIRCUIT,               // Круговая
    AMRAP,                 // As Many Rounds As Possible
    EMOM,                  // Every Minute On the Minute
    TABATA,                // Табата
    HIIT,                  // High Intensity Interval Training
    LADDER,                // Лестница (увеличение/уменьшение)
    PYRAMID,               // Пирамида
    DROP_SET,              // Дроп-сеты
    REST_PAUSE,            // Отдых-пауза
    CLUSTER,               // Кластерные сеты
    TIME_BASED,            // На время
    REP_BASED,             // На повторения
    CHIPPER,               // Чиппер (список упражнений)
    FOR_TIME,              // На время (CrossFit)
    ROUNDS_FOR_TIME,       // Раунды на время
    MAX_EFFORT,            // Максимальное усилие
    ENDURANCE              // Выносливость
}

enum class WorkoutPhaseType {
    WARMUP,                // Разминка
    MAIN,                  // Основная часть
    COOLDOWN,              // Заминка
    STRETCHING,            // Растяжка
    MOBILITY,              // Мобильность
    ACTIVATION,            // Активация
    SKILL_WORK             // Работа над техникой
}

enum class WorkoutIntensity {
    VERY_LIGHT,            // Очень легкая (50-60% от макс)
    LIGHT,                 // Легкая (60-70%)
    MODERATE,              // Умеренная (70-80%)
    HARD,                  // Тяжелая (80-90%)
    VERY_HARD,             // Очень тяжелая (90-95%)
    MAXIMAL               // Максимальная (95-100%)
}

enum class WorkoutDifficulty {
    EASY,
    MODERATE,
    HARD,
    EXTREME
}

enum class RestPeriodType {
    SECONDS,               // В секундах
    MINUTES,               // В минутах
    UNTIL_RECOVERED,       // До восстановления
    ACTIVE_REST,           // Активный отдых
    NO_REST               // Без отдыха
}
