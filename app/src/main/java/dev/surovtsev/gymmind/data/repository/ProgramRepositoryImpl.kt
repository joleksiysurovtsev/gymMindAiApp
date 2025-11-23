package dev.surovtsev.gymmind.data.repository

import dev.surovtsev.gymmind.domain.model.*
import dev.surovtsev.gymmind.domain.model.enums.*
import dev.surovtsev.gymmind.domain.repository.ProgramRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRepositoryImpl @Inject constructor() : ProgramRepository {

    private val mockPrograms = listOf(
        // 1. Программа для новичков - бесплатная
        ProgramModel(
            id = "program_beginner_gym",
            name = "Старт в зале",
            description = "12-недельная программа для новичков, которые впервые пришли в тренажерный зал. Полное руководство по технике базовых упражнений, прогрессивная нагрузка и безопасный старт.",
            shortDescription = "Идеальный старт для новичков в зале",
            type = ProgramType.GYM,
            scheduleType = ProgramScheduleType.CYCLIC_WEEKLY,
            difficulty = ProgramDifficulty.BEGINNER,
            goals = listOf(ProgramGoal.GENERAL_FITNESS, ProgramGoal.STRENGTH),
            duration = ProgramDuration(
                weeks = 12,
                sessionsPerWeek = 3,
                minutesPerSession = 60,
                totalSessions = 36
            ),
            requiredEquipment = listOf(
                Equipment.BARBELL,
                Equipment.DUMBBELL,
                Equipment.BENCH
            ),
            targetAudience = "Новички без опыта тренировок",
            tags = listOf("Новичкам", "База", "Техника", "Фулбоди"),
            structure = ProgramStructure(
                phases = listOf(
                    ProgramPhase(
                        id = "phase1",
                        name = "Адаптация",
                        weekStart = 1,
                        weekEnd = 4,
                        focus = "Изучение техники и адаптация организма"
                    )
                ),
                workoutSchedule = WorkoutSchedule(
                    type = ScheduleType.CYCLIC,
                    pattern = SchedulePattern(
                        cycleDays = 7,
                        workoutDays = listOf(1, 3, 5),
                        workoutIds = listOf("workout_a", "workout_b", "workout_c")
                    )
                ),
                progressionStrategy = ProgressionStrategy(
                    type = ProgressionType.LINEAR,
                    parameters = mapOf("incrementPercentage" to 2.5)
                ),
                deloadWeeks = listOf(4, 8)
            ),
            creatorId = "gymmind_team",
            isPublic = true,
            isVerified = true,
            isFeatured = true,
            isPremium = false,
            statistics = ProgramStatistics(
                totalEnrollments = 1542,
                completionRate = 0.78f,
                averageProgress = 0.65f
            ),
            reviews = ProgramReviews(
                averageRating = 4.7f,
                totalReviews = 312
            )
        ),

        // 2. Программа набора массы - премиум
        ProgramModel(
            id = "program_muscle_gain",
            name = "Масса за 16 недель",
            description = "Интенсивная 16-недельная программа для набора мышечной массы. Включает продвинутые техники, периодизацию нагрузки и индивидуальные рекомендации по питанию.",
            shortDescription = "Серьезный набор массы для опытных",
            type = ProgramType.BODYBUILDING,
            scheduleType = ProgramScheduleType.FIXED_DURATION,
            difficulty = ProgramDifficulty.INTERMEDIATE,
            goals = listOf(ProgramGoal.MUSCLE_GAIN, ProgramGoal.STRENGTH),
            duration = ProgramDuration(
                weeks = 16,
                sessionsPerWeek = 4,
                minutesPerSession = 75,
                totalSessions = 64
            ),
            requiredEquipment = listOf(
                Equipment.BARBELL,
                Equipment.DUMBBELL,
                Equipment.CABLE,
                Equipment.MACHINE
            ),
            targetAudience = "Атлеты среднего уровня с опытом от 1 года",
            tags = listOf("Масса", "Сплит", "Продвинутый", "Гипертрофия"),
            structure = ProgramStructure(
                phases = listOf(
                    ProgramPhase(
                        id = "phase1",
                        name = "Объемная фаза",
                        weekStart = 1,
                        weekEnd = 6,
                        focus = "Высокий объем для роста мышц"
                    )
                ),
                workoutSchedule = WorkoutSchedule(
                    type = ScheduleType.FIXED,
                    fixedSchedule = mapOf(
                        1 to "chest_triceps",
                        2 to "back_biceps",
                        4 to "legs",
                        5 to "shoulders"
                    )
                ),
                progressionStrategy = ProgressionStrategy(
                    type = ProgressionType.WAVE,
                    parameters = mapOf("incrementPercentage" to 5.0)
                ),
                deloadWeeks = listOf(6, 12)
            ),
            creatorId = "pro_coach",
            isPublic = true,
            isVerified = true,
            isFeatured = true,
            isPremium = true,
            price = 2990f,
            currency = "RUB",
            statistics = ProgramStatistics(
                totalEnrollments = 847,
                completionRate = 0.82f,
                averageProgress = 0.71f
            ),
            reviews = ProgramReviews(
                averageRating = 4.9f,
                totalReviews = 201
            )
        ),

        // 3. Домашние тренировки - бесплатная
        ProgramModel(
            id = "program_home",
            name = "Домашний фитнес",
            description = "8-недельная программа для тренировок дома без оборудования. Используем только вес собственного тела для развития силы, выносливости и гибкости.",
            shortDescription = "Эффективные тренировки дома без оборудования",
            type = ProgramType.HOME,
            scheduleType = ProgramScheduleType.FLEXIBLE,
            difficulty = ProgramDifficulty.BEGINNER,
            goals = listOf(ProgramGoal.GENERAL_FITNESS, ProgramGoal.WEIGHT_LOSS),
            duration = ProgramDuration(
                weeks = 8,
                sessionsPerWeek = 4,
                minutesPerSession = 45,
                totalSessions = 32
            ),
            requiredEquipment = emptyList(),
            targetAudience = "Все уровни подготовки",
            tags = listOf("Дома", "Без оборудования", "Для всех"),
            structure = ProgramStructure(
                phases = listOf(
                    ProgramPhase(
                        id = "phase1",
                        name = "Базовая подготовка",
                        weekStart = 1,
                        weekEnd = 4,
                        focus = "Основы движения и выносливость"
                    )
                ),
                workoutSchedule = WorkoutSchedule(
                    type = ScheduleType.FLEXIBLE,
                    rules = listOf(
                        ScheduleRule(RuleType.MIN_REST_DAYS, "1")
                    )
                ),
                progressionStrategy = ProgressionStrategy(
                    type = ProgressionType.LINEAR,
                    parameters = mapOf("targetRepsIncrease" to 2)
                )
            ),
            creatorId = "gymmind_team",
            isPublic = true,
            isVerified = true,
            isFeatured = true,
            isPremium = false,
            statistics = ProgramStatistics(
                totalEnrollments = 3241,
                completionRate = 0.75f,
                averageProgress = 0.68f
            ),
            reviews = ProgramReviews(
                averageRating = 4.6f,
                totalReviews = 782
            )
        ),

        // 4. Йога - бесплатная
        ProgramModel(
            id = "program_yoga",
            name = "Йога: первые шаги",
            description = "6-недельная программа по йоге для абсолютных новичков. Изучение базовых асан, правильного дыхания и развитие гибкости.",
            shortDescription = "Знакомство с йогой для новичков",
            type = ProgramType.YOGA,
            scheduleType = ProgramScheduleType.FLEXIBLE,
            difficulty = ProgramDifficulty.BEGINNER,
            goals = listOf(ProgramGoal.FLEXIBILITY, ProgramGoal.STRESS_RELIEF, ProgramGoal.HEALTH),
            duration = ProgramDuration(
                weeks = 6,
                sessionsPerWeek = 3,
                minutesPerSession = 40,
                totalSessions = 18
            ),
            requiredEquipment = listOf(Equipment.NONE),
            targetAudience = "Все желающие без ограничений",
            tags = listOf("Йога", "Гибкость", "Релакс"),
            structure = ProgramStructure(
                phases = listOf(
                    ProgramPhase(
                        id = "phase1",
                        name = "Основы",
                        weekStart = 1,
                        weekEnd = 3,
                        focus = "Базовые асаны и дыхание"
                    )
                ),
                workoutSchedule = WorkoutSchedule(
                    type = ScheduleType.FLEXIBLE
                ),
                progressionStrategy = ProgressionStrategy(
                    type = ProgressionType.LINEAR,
                    parameters = mapOf("focusOnTechnique" to true)
                )
            ),
            creatorId = "yoga_instructor",
            isPublic = true,
            isVerified = true,
            isFeatured = true,
            isPremium = false,
            statistics = ProgramStatistics(
                totalEnrollments = 2103,
                completionRate = 0.81f,
                averageProgress = 0.72f
            ),
            reviews = ProgramReviews(
                averageRating = 4.8f,
                totalReviews = 521
            )
        ),

        // 5. HIIT - премиум
        ProgramModel(
            id = "program_hiit",
            name = "Жиросжигание Pro",
            description = "Интенсивная 12-недельная программа для максимального жиросжигания. Комбинация HIIT тренировок, силовых упражнений и кардио для быстрых результатов.",
            shortDescription = "Максимальное жиросжигание за 12 недель",
            type = ProgramType.HIIT,
            scheduleType = ProgramScheduleType.FIXED_DURATION,
            difficulty = ProgramDifficulty.INTERMEDIATE,
            goals = listOf(ProgramGoal.WEIGHT_LOSS, ProgramGoal.ENDURANCE),
            duration = ProgramDuration(
                weeks = 12,
                sessionsPerWeek = 5,
                minutesPerSession = 50,
                totalSessions = 60
            ),
            requiredEquipment = listOf(
                Equipment.DUMBBELL,
                Equipment.KETTLEBELL,
                Equipment.ROPE
            ),
            targetAudience = "Атлеты с базовой подготовкой",
            tags = listOf("HIIT", "Похудение", "Кардио", "Интенсив"),
            structure = ProgramStructure(
                phases = listOf(
                    ProgramPhase(
                        id = "phase1",
                        name = "Адаптация к HIIT",
                        weekStart = 1,
                        weekEnd = 3,
                        focus = "Привыкание к высокоинтенсивным нагрузкам"
                    )
                ),
                workoutSchedule = WorkoutSchedule(
                    type = ScheduleType.CYCLIC,
                    pattern = SchedulePattern(
                        cycleDays = 7,
                        workoutDays = listOf(1, 2, 3, 5, 6),
                        workoutIds = listOf("hiit1", "hiit2", "hiit3", "hiit4", "hiit5")
                    )
                ),
                progressionStrategy = ProgressionStrategy(
                    type = ProgressionType.LINEAR,
                    parameters = mapOf("incrementPercentage" to 5.0)
                ),
                deloadWeeks = listOf(6)
            ),
            creatorId = "coach_pro",
            isPublic = true,
            isVerified = true,
            isFeatured = false,
            isPremium = true,
            price = 3490f,
            currency = "RUB",
            statistics = ProgramStatistics(
                totalEnrollments = 612,
                completionRate = 0.73f,
                averageProgress = 0.64f
            ),
            reviews = ProgramReviews(
                averageRating = 4.8f,
                totalReviews = 143
            )
        )
    )

    override fun getAllPrograms(): Flow<List<ProgramModel>> = flowOf(mockPrograms)

    override suspend fun getProgramById(programId: String): ProgramModel? {
        return mockPrograms.find { it.id == programId }
    }

    override fun getProgramsByType(type: ProgramType): Flow<List<ProgramModel>> {
        return flowOf(mockPrograms.filter { it.type == type })
    }

    override fun getProgramsByGoal(goal: ProgramGoal): Flow<List<ProgramModel>> {
        return flowOf(mockPrograms.filter { it.goals.contains(goal) })
    }

    override fun getProgramsByDifficulty(difficulty: ProgramDifficulty): Flow<List<ProgramModel>> {
        return flowOf(mockPrograms.filter { it.difficulty == difficulty })
    }

    override fun getFreePrograms(): Flow<List<ProgramModel>> {
        return flowOf(mockPrograms.filter { !it.isPremium })
    }

    override fun getPremiumPrograms(): Flow<List<ProgramModel>> {
        return flowOf(mockPrograms.filter { it.isPremium })
    }

    override fun getFeaturedPrograms(): Flow<List<ProgramModel>> {
        return flowOf(mockPrograms.filter { it.isFeatured })
    }
}
