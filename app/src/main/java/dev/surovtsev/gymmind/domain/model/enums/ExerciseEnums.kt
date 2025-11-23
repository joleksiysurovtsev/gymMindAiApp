package dev.surovtsev.gymmind.domain.model.enums

/**
 * Exercise Types & Categories - Типы упражнений
 */
enum class ExerciseType {
    STRENGTH,              // Силовые
    CARDIO,                // Кардио
    FLEXIBILITY,           // Растяжка/гибкость
    BALANCE,               // Баланс
    PLYOMETRIC,            // Плиометрические
    ISOMETRIC,             // Изометрические
    CALISTHENICS,          // Калистеника
    OLYMPIC,               // Тяжелая атлетика
    POWERLIFTING,          // Пауэрлифтинг
    BODYWEIGHT,            // С собственным весом
    WEIGHTED,              // С отягощением
    RESISTANCE_BAND,       // С резиновыми лентами
    MACHINE,               // На тренажерах
    SPORT_SPECIFIC,        // Спортивно-специфичные
    REHABILITATION,        // Реабилитационные
    WARMUP,                // Разминка
    COOLDOWN              // Заминка
}

enum class MuscleGroup {
    // Основные группы
    CHEST,                 // Грудь
    BACK,                  // Спина
    SHOULDERS,             // Плечи
    BICEPS,                // Бицепс
    TRICEPS,               // Трицепс
    FOREARMS,              // Предплечья
    ABS,                   // Пресс
    CORE,                  // Кор
    QUADRICEPS,            // Квадрицепс
    HAMSTRINGS,            // Задняя поверхность бедра
    GLUTES,                // Ягодицы
    CALVES,                // Икры
    HIP_FLEXORS,           // Сгибатели бедра
    ADDUCTORS,             // Приводящие мышцы
    ABDUCTORS,             // Отводящие мышцы
    LOWER_BACK,            // Поясница
    UPPER_BACK,            // Верх спины
    MIDDLE_BACK,           // Середина спины
    NECK,                  // Шея
    FULL_BODY              // Все тело
}

enum class Equipment {
    NONE,                  // Без оборудования
    BARBELL,               // Штанга
    DUMBBELL,              // Гантели
    KETTLEBELL,            // Гири
    CABLE,                 // Кроссовер/блоки
    MACHINE,               // Тренажер
    RESISTANCE_BAND,       // Резиновые ленты
    PULL_UP_BAR,           // Турник
    DIP_BARS,              // Брусья
    BENCH,                 // Скамья
    STABILITY_BALL,        // Фитбол
    MEDICINE_BALL,         // Медбол
    FOAM_ROLLER,           // Валик
    TRX,                   // TRX петли
    BOX,                   // Тумба/ящик
    ROPE,                  // Канат
    SLED,                  // Сани
    TREADMILL,             // Беговая дорожка
    BIKE,                  // Велотренажер
    ROWING_MACHINE,        // Гребной тренажер
    ELLIPTICAL,            // Эллиптический тренажер
    SMITH_MACHINE,         // Машина Смита
    POWER_RACK,            // Силовая рама
    PLATES,                // Блины
    CHAINS,                // Цепи
    PARALLETTES,           // Параллеты
    AB_WHEEL,              // Ролик для пресса
    RINGS,                 // Кольца
    DUMBBELLS,             // Deprecated - use DUMBBELL
    CARDIO_MACHINES,       // Deprecated - use specific machine type
    MACHINES               // Deprecated - use MACHINE
}

enum class ExerciseForce {
    PUSH,                  // Толкающие
    PULL,                  // Тянущие
    STATIC,                // Статические
    DYNAMIC,               // Динамические
    EXPLOSIVE              // Взрывные
}

enum class ExerciseMechanic {
    COMPOUND,              // Многосуставные
    ISOLATION,             // Изолирующие
    ISOMETRIC              // Изометрические
}

enum class ExerciseDifficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}
