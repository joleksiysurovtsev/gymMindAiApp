package dev.surovtsev.gymmind.domain.model.enums

/**
 * Metrics & Measurements - Метрики и измерения
 */
enum class MetricType {
    REPS,                  // Повторения
    SETS,                  // Подходы
    WEIGHT,                // Вес
    TIME,                  // Время
    DISTANCE,              // Дистанция
    SPEED,                 // Скорость
    PACE,                  // Темп
    HEART_RATE,            // Пульс
    CALORIES,              // Калории
    POWER,                 // Мощность (ватты)
    CADENCE,               // Каденс
    ELEVATION,             // Набор высоты
    ROUNDS,                // Раунды
    HEIGHT,                // Высота (для прыжков)
    HOLDS,                 // Удержания
    BREATHS,               // Дыхание (для йоги)
    STROKES,               // Гребки (плавание)
    STEPS,                 // Шаги
    FLOORS,                // Этажи
    RPE,                   // Уровень воспринимаемой нагрузки (1-10)
    SWOLF                  // Эффективность плавания
}

enum class WeightUnit {
    KG,                    // Килограммы
    LB,                    // Фунты
    BODYWEIGHT,            // Вес тела
    PERCENTAGE_1RM         // Процент от 1RM
}

enum class DistanceUnit {
    METERS,
    KILOMETERS,
    MILES,
    YARDS,
    FEET,
    LAPS,                  // Круги (бассейн/стадион)
    FLOORS                 // Этажи
}

enum class TimeUnit {
    SECONDS,
    MINUTES,
    HOURS,
    MILLISECONDS
}

enum class SpeedUnit {
    KMH,                   // км/ч
    MPH,                   // мили/ч
    MIN_PER_KM,            // мин/км
    MIN_PER_MILE,          // мин/миля
    MPS                    // м/с
}

enum class HeightUnit {
    CM,                    // Сантиметры
    METERS,                // Метры
    FEET,                  // Футы
    INCHES                 // Дюймы
}

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT
}

enum class HeartRateZone {
    ZONE_1_RECOVERY,        // 50-60% max HR
    ZONE_2_FAT_BURN,        // 60-70%
    ZONE_3_CARDIO,          // 70-80%
    ZONE_4_ANAEROBIC,       // 80-90%
    ZONE_5_MAX_EFFORT       // 90-100%
}
