# 🏋️ Fitness App Data Architecture & Models
## Полная спецификация для Android приложения

---

## 📋 Оглавление
1. [Enums - Перечисления](#enums)
2. [Core Data Models - Основные модели данных](#core-data-models)
3. [Room Database Entities - Сущности базы данных](#room-entities)
4. [Junction Tables - Связующие таблицы](#junction-tables)
5. [DAO Interfaces - Интерфейсы доступа к данным](#dao-interfaces)
6. [Repository Pattern - Паттерн репозитория](#repository-pattern)
7. [Database Configuration - Конфигурация БД](#database-configuration)
8. [Migration Strategies - Стратегии миграции](#migration-strategies)

---

## <a name="enums"></a>🎯 1. Enums - Перечисления

### 1.1 Program Types - Типы программ

```kotlin
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
```

### 1.2 Exercise Types & Categories - Типы упражнений

```kotlin
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
    RINGS                  // Кольца
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
```

### 1.3 Workout & Training Enums - Тренировочные перечисления

```kotlin
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

enum class WorkoutPhase {
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

enum class RestPeriodType {
    SECONDS,               // В секундах
    MINUTES,               // В минутах
    UNTIL_RECOVERED,       // До восстановления
    ACTIVE_REST,           // Активный отдых
    NO_REST               // Без отдыха
}
```

### 1.4 Metrics & Measurements - Метрики и измерения

```kotlin
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
```

### 1.5 Gamification & Social - Геймификация и социальные функции

```kotlin
enum class AchievementType {
    MILESTONE,             // Достижение вехи (100 тренировок)
    STREAK,                // Серия (7 дней подряд)
    PERSONAL_RECORD,       // Личный рекорд
    CHALLENGE,             // Выполнение челленджа
    SOCIAL,                // Социальное (100 подписчиков)
    SKILL,                 // Освоение навыка
    VOLUME,                // Объем (1000 км за месяц)
    CONSISTENCY,           // Постоянство
    IMPROVEMENT,           // Улучшение результатов
    SPECIAL_EVENT,         // Специальное событие
    FIRST_TIME,            // Первый раз
    COLLECTION             // Коллекция (все упражнения группы)
}

enum class BadgeRarity {
    COMMON,                // Обычный (70% пользователей)
    UNCOMMON,              // Необычный (30%)
    RARE,                  // Редкий (10%)
    EPIC,                  // Эпический (3%)
    LEGENDARY,             // Легендарный (1%)
    MYTHIC                 // Мифический (0.1%)
}

enum class ChallengeType {
    DISTANCE,              // На дистанцию
    TIME,                  // На время
    FREQUENCY,             // Частота (12 тренировок в месяц)
    VOLUME,                // Объем
    CONSISTENCY,           // Постоянство
    IMPROVEMENT,           // Улучшение
    TEAM,                  // Командный
    COMMUNITY,             // Сообщества
    SEASONAL,              // Сезонный
    MONTHLY,               // Месячный
    WEEKLY,                // Недельный
    CUSTOM                 // Пользовательский
}

enum class SocialInteractionType {
    FOLLOW,                // Подписка
    LIKE,                  // Лайк
    COMMENT,               // Комментарий
    SHARE,                 // Поделиться
    KUDOS,                 // Респект (как в Strava)
    HIGH_FIVE,             // Дай пять
    CHALLENGE,             // Вызов
    WORKOUT_TOGETHER       // Тренировка вместе
}

enum class PrivacyLevel {
    PUBLIC,                // Публичный
    FRIENDS_ONLY,          // Только друзья
    PRIVATE,               // Приватный
    CUSTOM                 // Настраиваемый
}
```

---

## <a name="core-data-models"></a>📊 2. Core Data Models - Основные модели данных

### 2.1 User Model - Модель пользователя

```kotlin
data class User(
    val id: String,                           // UUID
    val username: String,                     // Уникальное имя пользователя
    val email: String,
    val fullName: String?,
    val avatarUrl: String?,
    val bio: String?,
    val dateOfBirth: LocalDate?,
    val gender: Gender?,
    val height: Float?,                       // в сантиметрах
    val weight: Float?,                       // в килограммах
    val activityLevel: ActivityLevel?,
    val fitnessGoals: List<ProgramGoal>,
    val preferredUnits: UnitsPreference,
    val timezone: String,
    val isPremium: Boolean = false,
    val isVerified: Boolean = false,
    val privacySettings: PrivacySettings,
    val notificationSettings: NotificationSettings,
    val createdAt: Instant,
    val updatedAt: Instant,
    val lastActiveAt: Instant?,
    
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

enum class Gender {
    MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
}

enum class ActivityLevel {
    SEDENTARY,          // Малоподвижный
    LIGHTLY_ACTIVE,     // Легкая активность
    MODERATELY_ACTIVE,  // Умеренная активность
    VERY_ACTIVE,        // Высокая активность
    EXTREMELY_ACTIVE    // Очень высокая активность
}
```

### 2.2 Exercise Model - Модель упражнения

```kotlin
data class Exercise(
    val id: String,                           // UUID
    val name: String,
    val description: String?,
    val instructions: List<String>,           // Пошаговые инструкции
    val tips: List<String>,                   // Советы по выполнению
    val warnings: List<String>,               // Предупреждения
    val type: ExerciseType,
    val category: ExerciseCategory,
    val primaryMuscles: List<MuscleGroup>,
    val secondaryMuscles: List<MuscleGroup>,
    val equipment: List<Equipment>,
    val force: ExerciseForce?,
    val mechanic: ExerciseMechanic?,
    val difficulty: ExerciseDifficulty,
    val mediaAssets: ExerciseMedia,
    val metrics: ExerciseMetrics,
    val tags: List<String>,
    val isBodyweight: Boolean,
    val isCompound: Boolean,
    val isCardio: Boolean,
    val alternatives: List<String>,            // IDs альтернативных упражнений
    val progressions: List<String>,           // IDs упражнений-прогрессий
    val regressions: List<String>,            // IDs упражнений-регрессий
    val creatorId: String?,                   // Null для системных упражнений
    val isPublic: Boolean = true,
    val isVerified: Boolean = false,          // Проверено тренерами
    val popularityScore: Float = 0f,
    val averageRating: Float = 0f,
    val ratingsCount: Int = 0,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class ExerciseMedia(
    val thumbnailUrl: String?,
    val imageUrls: List<String>,
    val videoUrl: String?,
    val animationUrl: String?,                // GIF или анимация
    val muscleMapUrl: String?,                // Изображение работающих мышц
    val audioGuideUrl: String?                // Аудио-инструкция
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

enum class ExerciseDifficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}

enum class HeartRateZone {
    ZONE_1_RECOVERY,        // 50-60% max HR
    ZONE_2_FAT_BURN,        // 60-70%
    ZONE_3_CARDIO,          // 70-80%
    ZONE_4_ANAEROBIC,       // 80-90%
    ZONE_5_MAX_EFFORT       // 90-100%
}
```

### 2.3 Workout Model - Модель тренировки

```kotlin
data class Workout(
    val id: String,
    val name: String,
    val description: String?,
    val type: WorkoutType,
    val difficulty: WorkoutDifficulty,
    val estimatedDuration: Int,               // в минутах
    val targetMuscles: List<MuscleGroup>,
    val requiredEquipment: List<Equipment>,
    val phases: List<WorkoutPhase>,
    val imageUrl: String?,
    val tags: List<String>,
    val instructions: String?,
    val warmupNotes: String?,
    val cooldownNotes: String?,
    val creatorId: String?,
    val programId: String?,                   // Если часть программы
    val isPublic: Boolean = true,
    val isTemplate: Boolean = false,          // Шаблон для копирования
    val totalVolume: Float? = null,           // Общий тоннаж
    val estimatedCalories: Int? = null,
    val intensityLevel: WorkoutIntensity,
    val restBetweenExercises: Int?,           // в секундах
    val popularityScore: Float = 0f,
    val completionCount: Int = 0,
    val averageRating: Float = 0f,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class WorkoutPhase(
    val id: String,
    val name: String,                         // "Разминка", "Основная часть"
    val type: WorkoutPhaseType,
    val exercises: List<WorkoutExercise>,
    val duration: Int?,                       // в минутах
    val notes: String?
)

data class WorkoutExercise(
    val id: String,
    val exerciseId: String,
    val orderIndex: Int,                      // Порядок в тренировке
    val sets: List<ExerciseSet>,
    val restAfter: Int?,                      // Отдых после упражнения в секундах
    val notes: String?,
    val supersetGroupId: String?,             // Для группировки суперсетов
    val circuitGroupId: String?,              // Для круговых
    val isOptional: Boolean = false,
    val alternatives: List<String>            // IDs альтернативных упражнений
)

data class ExerciseSet(
    val setNumber: Int,
    val targetReps: Int?,
    val targetWeight: Float?,
    val targetTime: Int?,                     // в секундах
    val targetDistance: Float?,
    val minReps: Int?,                       // Для диапазона
    val maxReps: Int?,
    val rpe: Int?,                           // Rate of Perceived Exertion (1-10)
    val tempo: String?,                      // "3-1-1-0" (эксцентрик-пауза-концентрик-пауза)
    val dropSetWeight: Float?,               // Для дроп-сетов
    val isWarmup: Boolean = false,
    val isAmrap: Boolean = false,            // As Many Reps As Possible
    val notes: String?
)

enum class WorkoutDifficulty {
    EASY,
    MODERATE,
    HARD,
    EXTREME
}

enum class WorkoutPhaseType {
    WARMUP,
    MAIN,
    COOLDOWN,
    STRETCHING,
    SKILL_WORK,
    MOBILITY
}
```

### 2.4 Program Model - Модель программы тренировок

```kotlin
data class Program(
    val id: String,
    val name: String,
    val description: String,
    val shortDescription: String?,            // Для карточек
    val type: ProgramType,
    val scheduleType: ProgramScheduleType,
    val difficulty: ProgramDifficulty,
    val goals: List<ProgramGoal>,
    val duration: ProgramDuration,
    val requiredEquipment: List<Equipment>,
    val targetAudience: String?,              // "Начинающие", "Женщины 30+"
    val prerequisites: List<String>,          // Предварительные требования
    val imageUrl: String?,
    val videoPreviewUrl: String?,
    val tags: List<String>,
    val structure: ProgramStructure,
    val creatorId: String,
    val originalProgramId: String?,           // Если форк
    val isPublic: Boolean = true,
    val isVerified: Boolean = false,
    val isFeatured: Boolean = false,
    val isPremium: Boolean = false,
    val price: Float? = null,
    val currency: String? = null,
    val statistics: ProgramStatistics,
    val reviews: ProgramReviews,
    val createdAt: Instant,
    val updatedAt: Instant,
    val publishedAt: Instant?
)

data class ProgramDuration(
    val weeks: Int?,                          // Для фиксированных программ
    val days: Int?,                           // Альтернатива неделям
    val isOngoing: Boolean = false,           // Для циклических программ
    val sessionsPerWeek: Int?,
    val minutesPerSession: Int?,              // Средняя длительность тренировки
    val totalSessions: Int?
)

data class ProgramStructure(
    val phases: List<ProgramPhase>,           // Фазы программы
    val workoutSchedule: WorkoutSchedule,
    val progressionStrategy: ProgressionStrategy,
    val deloadWeeks: List<Int>,               // Недели разгрузки
    val testingDays: List<TestingDay>         // Дни тестирования
)

data class ProgramPhase(
    val id: String,
    val name: String,                         // "Адаптация", "База", "Пик"
    val weekStart: Int,
    val weekEnd: Int,
    val focus: String,                        // "Техника", "Объем", "Интенсивность"
    val description: String?,
    val workoutIds: List<String>
)

data class WorkoutSchedule(
    val type: ScheduleType,
    val pattern: SchedulePattern?,            // Для циклических
    val fixedSchedule: Map<Int, String>?,     // День -> WorkoutId для фиксированных
    val rules: List<ScheduleRule>             // Правила планирования
)

enum class ScheduleType {
    FIXED,                  // Жесткое расписание
    CYCLIC,                 // Повторяющийся цикл
    FLEXIBLE,               // Гибкое
    ADAPTIVE                // Адаптивное (AI)
}

data class SchedulePattern(
    val cycleDays: Int,                       // Длина цикла
    val workoutDays: List<Int>,               // Дни тренировок в цикле
    val workoutIds: List<String>              // Соответствующие тренировки
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
    val parameters: Map<String, Any>
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
    val exercises: List<String>,              // IDs упражнений для тестирования
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
```

---

## <a name="room-entities"></a>🗄️ 3. Room Database Entities

### 3.1 User Entity

```kotlin
import androidx.room.*
import java.time.Instant
import java.time.LocalDate

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

// Type Converters
@ProvidersTypeConverters
class UserConverters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()
    
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? = 
        dateString?.let { LocalDate.parse(it) }
    
    @TypeConverter
    fun fromInstant(instant: Instant?): Long? = instant?.toEpochMilli()
    
    @TypeConverter
    fun toInstant(millis: Long?): Instant? = 
        millis?.let { Instant.ofEpochMilli(it) }
}
```

### 3.2 Exercise Entity

```kotlin
@Entity(
    tableName = "exercises",
    indices = [
        Index(value = ["name"]),
        Index(value = ["type"]),
        Index(value = ["primaryMuscles"]),
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
    val category: String?,
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

// Для полнотекстового поиска
@Entity(tableName = "exercise_fts")
@Fts4(contentEntity = ExerciseEntity::class)
data class ExerciseFts(
    val name: String,
    val description: String?,
    val tags: String?
)
```

### 3.3 Workout Entity

```kotlin
@Entity(
    tableName = "workouts",
    indices = [
        Index(value = ["name"]),
        Index(value = ["programId"]),
        Index(value = ["creatorId"]),
        Index(value = ["createdAt"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["creatorId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class WorkoutEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val type: String,
    val difficulty: String,
    val estimatedDuration: Int,
    val targetMuscles: String,                // JSON array
    val requiredEquipment: String,            // JSON array
    val phases: String,                       // JSON array
    val imageUrl: String?,
    val tags: String?,                        // JSON array
    val instructions: String?,
    val warmupNotes: String?,
    val cooldownNotes: String?,
    val creatorId: String?,
    val programId: String?,
    val isPublic: Boolean = true,
    val isTemplate: Boolean = false,
    val totalVolume: Float?,
    val estimatedCalories: Int?,
    val intensityLevel: String,
    val restBetweenExercises: Int?,
    @ColumnInfo(index = true)
    val popularityScore: Float = 0f,
    val completionCount: Int = 0,
    val averageRating: Float = 0f,
    val createdAt: Instant,
    val updatedAt: Instant,
    
    // Sync
    @ColumnInfo(index = true)
    val isSynced: Boolean = false,
    val syncVersion: Int = 0
)
```

### 3.4 Program Entity

```kotlin
@Entity(
    tableName = "programs",
    indices = [
        Index(value = ["name"]),
        Index(value = ["type"]),
        Index(value = ["creatorId"]),
        Index(value = ["isPublic", "isVerified", "isFeatured"]),
        Index(value = ["createdAt"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["creatorId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["originalProgramId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class ProgramEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val shortDescription: String?,
    val type: String,
    val scheduleType: String,
    val difficulty: String,
    val goals: String,                        // JSON array
    val duration: String,                     // JSON object
    val requiredEquipment: String,            // JSON array
    val targetAudience: String?,
    val prerequisites: String?,               // JSON array
    val imageUrl: String?,
    val videoPreviewUrl: String?,
    val tags: String?,                        // JSON array
    val structure: String,                    // JSON object
    val creatorId: String,
    val originalProgramId: String?,
    val isPublic: Boolean = true,
    val isVerified: Boolean = false,
    val isFeatured: Boolean = false,
    val isPremium: Boolean = false,
    val price: Float?,
    val currency: String?,
    val statistics: String,                   // JSON object
    val reviews: String,                      // JSON object
    val createdAt: Instant,
    val updatedAt: Instant,
    val publishedAt: Instant?,
    
    // Sync
    @ColumnInfo(index = true)
    val isSynced: Boolean = false,
    val syncVersion: Int = 0
)
```

---

## <a name="junction-tables"></a>🔗 4. Junction Tables - Связующие таблицы

### 4.1 Workout-Exercise Junction

```kotlin
@Entity(
    tableName = "workout_exercises",
    primaryKeys = ["workoutId", "exerciseId", "orderIndex"],
    indices = [
        Index(value = ["workoutId"]),
        Index(value = ["exerciseId"]),
        Index(value = ["orderIndex"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutExerciseCrossRef(
    val workoutId: String,
    val exerciseId: String,
    val orderIndex: Int,
    val sets: String,                         // JSON array of sets
    val restAfter: Int?,
    val notes: String?,
    val supersetGroupId: String?,
    val circuitGroupId: String?,
    val isOptional: Boolean = false,
    val alternatives: String?,                // JSON array of exercise IDs
    val createdAt: Instant = Instant.now()
)
```

### 4.2 Program-Workout Junction

```kotlin
@Entity(
    tableName = "program_workouts",
    primaryKeys = ["programId", "workoutId", "scheduleDay"],
    indices = [
        Index(value = ["programId"]),
        Index(value = ["workoutId"]),
        Index(value = ["scheduleDay"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProgramWorkoutCrossRef(
    val programId: String,
    val workoutId: String,
    val scheduleDay: Int,                     // День в программе (1-based)
    val weekNumber: Int?,                     // Номер недели
    val phaseId: String?,                     // ID фазы программы
    val isOptional: Boolean = false,
    val alternatives: String?,                // JSON array
    val notes: String?,
    val createdAt: Instant = Instant.now()
)
```

### 4.3 User-Program Junction (Enrollments)

```kotlin
@Entity(
    tableName = "user_program_enrollments",
    primaryKeys = ["userId", "programId"],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["programId"]),
        Index(value = ["startedAt"]),
        Index(value = ["status"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserProgramEnrollment(
    val userId: String,
    val programId: String,
    val status: String,                       // ACTIVE, PAUSED, COMPLETED, ABANDONED
    val startedAt: Instant,
    val pausedAt: Instant?,
    val completedAt: Instant?,
    val currentDay: Int = 1,
    val currentWeek: Int = 1,
    val progressPercentage: Float = 0f,
    val completedWorkouts: String,            // JSON array of workout IDs
    val skippedWorkouts: String?,             // JSON array
    val modifications: String?,               // JSON object с изменениями
    val notes: String?,
    val rating: Float?,
    val review: String?,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)
```

---

## <a name="dao-interfaces"></a>💾 5. DAO Interfaces

### 5.1 User DAO

```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?
    
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserFlow(userId: String): Flow<UserEntity?>
    
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity?
    
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Delete
    suspend fun deleteUser(user: UserEntity)
    
    @Query("UPDATE users SET isSynced = 0 WHERE id = :userId")
    suspend fun markAsUnsynced(userId: String)
    
    @Query("SELECT * FROM users WHERE isSynced = 0")
    suspend fun getUnsyncedUsers(): List<UserEntity>
    
    @Transaction
    @Query("""
        UPDATE users 
        SET currentStreak = :streak, 
            longestStreak = CASE 
                WHEN :streak > longestStreak THEN :streak 
                ELSE longestStreak 
            END,
            lastActiveAt = :now
        WHERE id = :userId
    """)
    suspend fun updateStreak(userId: String, streak: Int, now: Instant)
    
    @Query("""
        UPDATE users 
        SET level = :level, 
            experiencePoints = :xp 
        WHERE id = :userId
    """)
    suspend fun updateLevelAndXp(userId: String, level: Int, xp: Int)
}
```

### 5.2 Exercise DAO

```kotlin
@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises")
    fun getAllExercises(): Flow<List<ExerciseEntity>>
    
    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: String): ExerciseEntity?
    
    @Query("""
        SELECT * FROM exercises 
        WHERE type = :type 
        ORDER BY popularityScore DESC
    """)
    fun getExercisesByType(type: String): Flow<List<ExerciseEntity>>
    
    @Query("""
        SELECT * FROM exercises 
        WHERE primaryMuscles LIKE '%' || :muscle || '%'
        OR secondaryMuscles LIKE '%' || :muscle || '%'
        ORDER BY popularityScore DESC
    """)
    fun getExercisesByMuscle(muscle: String): Flow<List<ExerciseEntity>>
    
    @Query("""
        SELECT * FROM exercises 
        WHERE equipment LIKE '%' || :equipment || '%'
        ORDER BY popularityScore DESC
    """)
    fun getExercisesByEquipment(equipment: String): Flow<List<ExerciseEntity>>
    
    @Query("""
        SELECT exercises.* FROM exercises
        JOIN exercise_fts ON exercises.id = exercise_fts.docid
        WHERE exercise_fts MATCH :query
        ORDER BY popularityScore DESC
    """)
    fun searchExercises(query: String): Flow<List<ExerciseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)
    
    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)
    
    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)
    
    @Query("UPDATE exercises SET isSynced = 0 WHERE id = :exerciseId")
    suspend fun markAsUnsynced(exerciseId: String)
    
    @Query("SELECT * FROM exercises WHERE isSynced = 0")
    suspend fun getUnsyncedExercises(): List<ExerciseEntity>
    
    @Query("""
        UPDATE exercises 
        SET popularityScore = popularityScore + 1 
        WHERE id = :exerciseId
    """)
    suspend fun incrementPopularity(exerciseId: String)
    
    @Query("""
        SELECT * FROM exercises 
        WHERE creatorId = :userId 
        ORDER BY createdAt DESC
    """)
    fun getUserExercises(userId: String): Flow<List<ExerciseEntity>>
}
```

### 5.3 Workout DAO

```kotlin
@Dao
interface WorkoutDao {
    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutWithExercises(workoutId: String): WorkoutWithExercises?
    
    @Query("SELECT * FROM workouts WHERE programId = :programId ORDER BY createdAt")
    fun getWorkoutsForProgram(programId: String): Flow<List<WorkoutEntity>>
    
    @Query("""
        SELECT * FROM workouts 
        WHERE creatorId = :userId 
        ORDER BY createdAt DESC
    """)
    fun getUserWorkouts(userId: String): Flow<List<WorkoutEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercises(exercises: List<WorkoutExerciseCrossRef>)
    
    @Transaction
    suspend fun insertWorkoutWithExercises(
        workout: WorkoutEntity,
        exercises: List<WorkoutExerciseCrossRef>
    ) {
        insertWorkout(workout)
        insertWorkoutExercises(exercises)
    }
    
    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)
    
    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)
    
    @Query("""
        UPDATE workouts 
        SET completionCount = completionCount + 1 
        WHERE id = :workoutId
    """)
    suspend fun incrementCompletionCount(workoutId: String)
}

// Data class for relationship
data class WorkoutWithExercises(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        parentColumn = "id",
        entity = ExerciseEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = WorkoutExerciseCrossRef::class,
            parentColumn = "workoutId",
            entityColumn = "exerciseId"
        )
    )
    val exercises: List<ExerciseEntity>
)
```

### 5.4 Program DAO

```kotlin
@Dao
interface ProgramDao {
    @Transaction
    @Query("SELECT * FROM programs WHERE id = :programId")
    suspend fun getProgramWithWorkouts(programId: String): ProgramWithWorkouts?
    
    @Query("""
        SELECT * FROM programs 
        WHERE isPublic = 1 AND isVerified = 1 
        ORDER BY 
            CASE WHEN isFeatured = 1 THEN 0 ELSE 1 END,
            popularityScore DESC
        LIMIT :limit
    """)
    fun getFeaturedPrograms(limit: Int = 20): Flow<List<ProgramEntity>>
    
    @Query("""
        SELECT * FROM programs 
        WHERE type = :type AND isPublic = 1 
        ORDER BY averageRating DESC
        LIMIT :limit
    """)
    fun getProgramsByType(type: String, limit: Int = 50): Flow<List<ProgramEntity>>
    
    @Query("""
        SELECT * FROM programs 
        WHERE goals LIKE '%' || :goal || '%' AND isPublic = 1 
        ORDER BY averageRating DESC
    """)
    fun getProgramsByGoal(goal: String): Flow<List<ProgramEntity>>
    
    @Transaction
    @Query("""
        SELECT p.* FROM programs p
        INNER JOIN user_program_enrollments e ON p.id = e.programId
        WHERE e.userId = :userId AND e.status = :status
        ORDER BY e.startedAt DESC
    """)
    fun getUserEnrolledPrograms(
        userId: String, 
        status: String = "ACTIVE"
    ): Flow<List<ProgramEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgram(program: ProgramEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgramWorkouts(workouts: List<ProgramWorkoutCrossRef>)
    
    @Transaction
    suspend fun insertProgramWithWorkouts(
        program: ProgramEntity,
        workouts: List<ProgramWorkoutCrossRef>
    ) {
        insertProgram(program)
        insertProgramWorkouts(workouts)
    }
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enrollUserInProgram(enrollment: UserProgramEnrollment)
    
    @Query("""
        UPDATE user_program_enrollments 
        SET status = :status, updatedAt = :now 
        WHERE userId = :userId AND programId = :programId
    """)
    suspend fun updateEnrollmentStatus(
        userId: String, 
        programId: String, 
        status: String,
        now: Instant
    )
}

// Data class for relationship
data class ProgramWithWorkouts(
    @Embedded val program: ProgramEntity,
    @Relation(
        parentColumn = "id",
        entity = WorkoutEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ProgramWorkoutCrossRef::class,
            parentColumn = "programId",
            entityColumn = "workoutId"
        )
    )
    val workouts: List<WorkoutEntity>
)
```

---

## <a name="repository-pattern"></a>🏗️ 6. Repository Pattern

### 6.1 Base Repository

```kotlin
abstract class BaseRepository<T> {
    protected abstract val dao: Any
    
    protected suspend fun <R> safeApiCall(
        apiCall: suspend () -> R
    ): Result<R> {
        return try {
            Result.success(apiCall())
        } catch (e: Exception) {
            Timber.e(e, "API call failed")
            Result.failure(e)
        }
    }
    
    protected fun <T> Flow<T>.asResult(): Flow<Result<T>> = map {
        Result.success(it)
    }.catch { e ->
        emit(Result.failure(e))
    }
}
```

### 6.2 Exercise Repository

```kotlin
@Singleton
class ExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val apiService: FitnessApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository<ExerciseEntity>() {
    
    override val dao = exerciseDao
    
    fun getAllExercises(): Flow<List<Exercise>> = 
        exerciseDao.getAllExercises()
            .map { entities -> 
                entities.map { it.toDomainModel() }
            }
            .flowOn(ioDispatcher)
    
    fun searchExercises(query: String): Flow<List<Exercise>> =
        exerciseDao.searchExercises(query)
            .map { entities ->
                entities.map { it.toDomainModel() }
            }
            .flowOn(ioDispatcher)
    
    suspend fun getExerciseById(id: String): Exercise? = withContext(ioDispatcher) {
        exerciseDao.getExerciseById(id)?.toDomainModel()
    }
    
    suspend fun syncExercises() = withContext(ioDispatcher) {
        try {
            // Получаем упражнения с сервера
            val remoteExercises = apiService.getExercises()
            
            // Конвертируем и сохраняем в БД
            val entities = remoteExercises.map { it.toEntity() }
            exerciseDao.insertExercises(entities)
            
            // Отправляем несинхронизированные локальные изменения
            val unsyncedExercises = exerciseDao.getUnsyncedExercises()
            unsyncedExercises.forEach { exercise ->
                val result = safeApiCall {
                    apiService.updateExercise(exercise.id, exercise.toDto())
                }
                if (result.isSuccess) {
                    exerciseDao.insertExercise(exercise.copy(isSynced = true))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to sync exercises")
        }
    }
    
    suspend fun createCustomExercise(exercise: Exercise): Result<Exercise> = 
        withContext(ioDispatcher) {
            val entity = exercise.toEntity().copy(
                id = UUID.randomUUID().toString(),
                isSynced = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
            
            exerciseDao.insertExercise(entity)
            
            // Пытаемся синхронизировать с сервером
            safeApiCall {
                apiService.createExercise(entity.toDto())
            }.onSuccess {
                exerciseDao.insertExercise(entity.copy(isSynced = true))
            }
            
            Result.success(entity.toDomainModel())
        }
}

// Extension functions for mapping
private fun ExerciseEntity.toDomainModel(): Exercise {
    return Exercise(
        id = id,
        name = name,
        description = description,
        instructions = Json.decodeFromString(instructions),
        tips = tips?.let { Json.decodeFromString(it) } ?: emptyList(),
        // ... map other fields
    )
}

private fun Exercise.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        name = name,
        description = description,
        instructions = Json.encodeToString(instructions),
        tips = tips.takeIf { it.isNotEmpty() }?.let { Json.encodeToString(it) },
        // ... map other fields
    )
}
```

### 6.3 Workout Repository

```kotlin
@Singleton
class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao,
    private val apiService: FitnessApiService,
    private val workManager: WorkManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository<WorkoutEntity>() {
    
    override val dao = workoutDao
    
    suspend fun getWorkoutWithExercises(workoutId: String): Workout? = 
        withContext(ioDispatcher) {
            workoutDao.getWorkoutWithExercises(workoutId)?.let { workoutWithExercises ->
                workoutWithExercises.toDomainModel()
            }
        }
    
    suspend fun createWorkout(workout: Workout): Result<Workout> = 
        withContext(ioDispatcher) {
            val workoutEntity = workout.toEntity().copy(
                id = UUID.randomUUID().toString(),
                isSynced = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
            
            val exerciseRefs = workout.exercises.mapIndexed { index, exercise ->
                WorkoutExerciseCrossRef(
                    workoutId = workoutEntity.id,
                    exerciseId = exercise.exerciseId,
                    orderIndex = index,
                    sets = Json.encodeToString(exercise.sets),
                    restAfter = exercise.restAfter,
                    notes = exercise.notes,
                    supersetGroupId = exercise.supersetGroupId,
                    circuitGroupId = exercise.circuitGroupId,
                    isOptional = exercise.isOptional,
                    alternatives = exercise.alternatives.takeIf { it.isNotEmpty() }
                        ?.let { Json.encodeToString(it) }
                )
            }
            
            workoutDao.insertWorkoutWithExercises(workoutEntity, exerciseRefs)
            
            // Schedule sync with WorkManager
            scheduleSyncWorkout(workoutEntity.id)
            
            Result.success(workout.copy(id = workoutEntity.id))
        }
    
    private fun scheduleSyncWorkout(workoutId: String) {
        val syncRequest = OneTimeWorkRequestBuilder<WorkoutSyncWorker>()
            .setInputData(
                workDataOf("workout_id" to workoutId)
            )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        
        workManager.enqueue(syncRequest)
    }
    
    suspend fun completeWorkout(
        workoutId: String,
        actualSets: List<ActualSet>
    ): Result<WorkoutCompletion> = withContext(ioDispatcher) {
        // Сохраняем результаты тренировки
        val completion = WorkoutCompletion(
            id = UUID.randomUUID().toString(),
            workoutId = workoutId,
            userId = getCurrentUserId(),
            startedAt = Instant.now().minusSeconds(3600), // Example
            completedAt = Instant.now(),
            actualSets = actualSets,
            totalVolume = calculateVolume(actualSets),
            caloriesBurned = calculateCalories(actualSets),
            notes = null
        )
        
        // Сохраняем в БД
        saveWorkoutCompletion(completion)
        
        // Обновляем счетчик выполнений
        workoutDao.incrementCompletionCount(workoutId)
        
        // Обновляем статистику пользователя
        updateUserStats(completion)
        
        // Проверяем достижения
        checkAchievements(completion)
        
        Result.success(completion)
    }
}
```

---

## <a name="database-configuration"></a>⚙️ 7. Database Configuration

### 7.1 Room Database Setup

```kotlin
@Database(
    entities = [
        UserEntity::class,
        ExerciseEntity::class,
        ExerciseFts::class,
        WorkoutEntity::class,
        ProgramEntity::class,
        WorkoutExerciseCrossRef::class,
        ProgramWorkoutCrossRef::class,
        UserProgramEnrollment::class,
        WorkoutCompletionEntity::class,
        PersonalRecordEntity::class,
        AchievementEntity::class,
        UserAchievementEntity::class,
        StreakEntity::class,
        ChallengeEntity::class,
        UserChallengeEntity::class
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
@TypeConverters(
    InstantConverter::class,
    LocalDateConverter::class,
    ListStringConverter::class,
    JsonConverter::class
)
abstract class FitnessDatabase : RoomDatabase() {
    
    // DAOs
    abstract fun userDao(): UserDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun programDao(): ProgramDao
    abstract fun completionDao(): WorkoutCompletionDao
    abstract fun achievementDao(): AchievementDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun statsDao(): StatsDao
    
    companion object {
        private const val DATABASE_NAME = "fitness_database"
        
        @Volatile
        private var INSTANCE: FitnessDatabase? = null
        
        fun getInstance(context: Context): FitnessDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        
        private fun buildDatabase(context: Context): FitnessDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                FitnessDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(DatabaseCallback())
                .addMigrations(
                    MIGRATION_1_2,
                    MIGRATION_2_3
                )
                .build()
        }
    }
    
    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Populate with initial data if needed
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context).apply {
                    // Seed initial exercises
                    seedInitialExercises()
                }
            }
        }
    }
}
```

### 7.2 Type Converters

```kotlin
class InstantConverter {
    @TypeConverter
    fun fromInstant(instant: Instant?): Long? = instant?.toEpochMilli()
    
    @TypeConverter
    fun toInstant(millis: Long?): Instant? = 
        millis?.let { Instant.ofEpochMilli(it) }
}

class LocalDateConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()
    
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? = 
        dateString?.let { LocalDate.parse(it) }
}

class ListStringConverter {
    @TypeConverter
    fun fromList(list: List<String>?): String? = 
        list?.let { Json.encodeToString(it) }
    
    @TypeConverter
    fun toList(json: String?): List<String>? = 
        json?.let { Json.decodeFromString(it) }
}

class JsonConverter {
    @TypeConverter
    fun fromJson(json: String?): Map<String, Any>? = 
        json?.let { Json.decodeFromString(it) }
    
    @TypeConverter
    fun toJson(map: Map<String, Any>?): String? = 
        map?.let { Json.encodeToString(it) }
}
```

---

## <a name="migration-strategies"></a>📈 8. Migration Strategies

### 8.1 Migration Examples

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Добавляем новую колонку для хранения заметок
        database.execSQL(
            "ALTER TABLE workouts ADD COLUMN user_notes TEXT"
        )
        
        // Создаем новую таблицу для истории тренировок
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS workout_history (
                id TEXT PRIMARY KEY NOT NULL,
                userId TEXT NOT NULL,
                workoutId TEXT NOT NULL,
                startedAt INTEGER NOT NULL,
                completedAt INTEGER NOT NULL,
                actualData TEXT NOT NULL,
                totalVolume REAL,
                caloriesBurned INTEGER,
                notes TEXT,
                isSynced INTEGER NOT NULL DEFAULT 0,
                createdAt INTEGER NOT NULL,
                FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE,
                FOREIGN KEY(workoutId) REFERENCES workouts(id) ON DELETE CASCADE
            )
        """)
        
        // Создаем индексы
        database.execSQL(
            "CREATE INDEX index_workout_history_userId ON workout_history(userId)"
        )
        database.execSQL(
            "CREATE INDEX index_workout_history_workoutId ON workout_history(workoutId)"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Переименовываем колонку (SQLite не поддерживает ALTER COLUMN)
        database.execSQL("""
            CREATE TABLE workouts_new (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                description TEXT,
                type TEXT NOT NULL,
                difficulty TEXT NOT NULL,
                duration_minutes INTEGER NOT NULL,
                -- остальные поля...
            )
        """)
        
        database.execSQL("""
            INSERT INTO workouts_new (id, name, description, type, difficulty, duration_minutes, ...)
            SELECT id, name, description, type, difficulty, estimatedDuration, ...
            FROM workouts
        """)
        
        database.execSQL("DROP TABLE workouts")
        database.execSQL("ALTER TABLE workouts_new RENAME TO workouts")
        
        // Восстанавливаем индексы
        database.execSQL(
            "CREATE INDEX index_workouts_name ON workouts(name)"
        )
    }
}
```

### 8.2 Migration Testing

```kotlin
@RunWith(AndroidJUnit4::class)
class MigrationTest {
    
    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        FitnessDatabase::class.java,
        listOf(Migration1to2(), Migration2to3())
    )
    
    @Test
    fun migrate1To2() {
        // Create database version 1
        var db = helper.createDatabase(TEST_DB, 1).apply {
            execSQL("""
                INSERT INTO workouts (id, name, type, difficulty, estimatedDuration)
                VALUES ('test-id', 'Test Workout', 'STRENGTH', 'BEGINNER', 30)
            """)
            close()
        }
        
        // Migrate to version 2
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
        
        // Verify data preserved and new column exists
        val cursor = db.query("SELECT * FROM workouts WHERE id = 'test-id'")
        assertTrue(cursor.moveToFirst())
        assertEquals("Test Workout", cursor.getString(cursor.getColumnIndex("name")))
        
        // Verify new column exists
        val columnIndex = cursor.getColumnIndex("user_notes")
        assertNotEquals(-1, columnIndex)
    }
    
    companion object {
        private const val TEST_DB = "migration-test"
    }
}
```

---

## 📝 Implementation Notes

### Performance Optimizations

1. **Индексация**: Создавайте индексы на всех foreign keys и полях, используемых в WHERE и ORDER BY
2. **Пагинация**: Используйте Paging 3 library для больших списков
3. **Кэширование**: Implement multi-level caching (Memory -> Room -> Network)
4. **Batch операции**: Используйте batch inserts/updates для массовых операций

### Sync Strategy

1. **Offline-first**: Все операции сначала выполняются локально
2. **WorkManager**: Для надежной фоновой синхронизации
3. **Conflict Resolution**: Last-write-wins с версионированием
4. **Incremental Sync**: Синхронизация только измененных данных

### Security Considerations

1. **Encryption**: Используйте SQLCipher для шифрования БД
2. **User Data**: Храните sensitive data в EncryptedSharedPreferences
3. **API Keys**: Никогда не храните в коде, используйте BuildConfig
4. **ProGuard**: Обфусцируйте код в production builds

### Testing Strategy

1. **Unit Tests**: Для бизнес-логики и mappers
2. **Integration Tests**: Для DAO и Repository
3. **UI Tests**: Для критических user flows
4. **Migration Tests**: Обязательно для каждой миграции БД

---

## 🚀 Next Steps

1. **Implement Data Layer**: Начните с Room entities и DAO
2. **Create Repositories**: Implement repository pattern с offline-first
3. **Setup WorkManager**: Для background sync
4. **Add Analytics**: Firebase Analytics или custom solution
5. **Implement Caching**: Multi-level caching strategy
6. **Setup CI/CD**: Automated testing и deployment
7. **Performance Monitoring**: Crashlytics, Performance Monitoring

---

## 📚 Resources

- [Room Database Guide](https://developer.android.com/training/data-storage/room)
- [Offline-first Architecture](https://developer.android.com/topic/architecture/data-layer/offline-first)
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Repository Pattern](https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern)

---

**Версия документа**: 1.0.0  
**Последнее обновление**: 2024  
**Автор**: AI Assistant for Fitness App Development