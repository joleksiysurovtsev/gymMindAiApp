package dev.surovtsev.gymmind.domain.model.enums

/**
 * Program Types - Типы программ
 */
enum class ProgramType {
    GYM,                    // Тренировки в зале
    HOME,                   // Домашние тренировки
    OUTDOOR,                // Уличные тренировки (воркаут)
    CALISTHENICS,          // Калистеника (турники/брусья)
    RUNNING,               // Беговые программы
    SWIMMING,              // Плавание
    CYCLING,               // Велосипед
    YOGA,                  // Йога
    PILATES,               // Пилатес
    CROSSFIT,              // Кроссфит
    MARTIAL_ARTS,          // Боевые искусства
    HIIT,                  // Высокоинтенсивные интервальные
    STRETCHING,            // Растяжка
    POWERLIFTING,          // Пауэрлифтинг
    BODYBUILDING,          // Бодибилдинг
    FUNCTIONAL,            // Функциональные тренировки
    REHABILITATION,        // Восстановительные
    MIXED                  // Смешанный тип
}

enum class ProgramScheduleType {
    FIXED_DURATION,        // Фиксированная длительность (например, 30 дней)
    CYCLIC_WEEKLY,         // Циклическая недельная (Пн, Ср, Пт)
    FLEXIBLE,              // Гибкий график
    DAILY,                 // Ежедневная
    ADAPTIVE,              // Адаптивная (подстраивается под пользователя)
    PERIODIC               // Периодическая (с перерывами)
}

enum class ProgramDifficulty {
    BEGINNER,              // Начинающий
    INTERMEDIATE,          // Средний уровень
    ADVANCED,              // Продвинутый
    EXPERT,                // Эксперт
    ADAPTIVE               // Адаптивная сложность
}

enum class ProgramGoal {
    WEIGHT_LOSS,           // Похудение
    MUSCLE_GAIN,           // Набор мышечной массы
    STRENGTH,              // Увеличение силы
    ENDURANCE,             // Выносливость
    FLEXIBILITY,           // Гибкость
    GENERAL_FITNESS,       // Общая физическая форма
    SPORT_SPECIFIC,        // Специфичная для спорта
    REHABILITATION,        // Реабилитация
    BODY_RECOMPOSITION,    // Рекомпозиция тела
    PERFORMANCE,           // Улучшение результатов
    HEALTH,                // Здоровье
    STRESS_RELIEF          // Снятие стресса
}

enum class ProgramStatus {
    DRAFT,                 // Черновик
    PUBLISHED,             // Опубликована
    ARCHIVED,              // В архиве
    PRIVATE,               // Приватная
    UNDER_REVIEW           // На модерации
}
