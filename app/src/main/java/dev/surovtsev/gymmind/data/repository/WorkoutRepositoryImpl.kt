package dev.surovtsev.gymmind.data.repository

import dev.surovtsev.gymmind.domain.model.*
import dev.surovtsev.gymmind.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepositoryImpl @Inject constructor() : WorkoutRepository {

    // Mock data: 2 free basic workouts + premium workouts
    private val mockWorkouts = listOf(
        // FREE BASIC WORKOUT 1: Full Body Beginner
        WorkoutPlan(
            id = "free_full_body_beginner",
            name = "Full Body Beginner",
            description = "Perfect for those just starting out. This workout targets all major muscle groups with simple, effective exercises.",
            category = WorkoutCategory.FREE_BASIC,
            difficulty = ExperienceLevel.BEGINNER,
            durationMinutes = 25,
            caloriesBurnEstimate = 180,
            tags = listOf("Full Body", "Beginner", "No Equipment"),
            exercises = listOf(
                Exercise(
                    id = "ex_bodyweight_squat",
                    name = "Bodyweight Squat",
                    description = "Basic squat using only bodyweight",
                    sets = 3,
                    reps = "10-12",
                    restSeconds = 60,
                    equipment = emptyList(),
                    muscleGroups = listOf("Legs", "Glutes"),
                    instructions = listOf(
                        "Stand with feet shoulder-width apart",
                        "Lower your body by bending knees",
                        "Keep chest up and back straight",
                        "Return to starting position"
                    )
                ),
                Exercise(
                    id = "ex_push_up",
                    name = "Push-Up (or Knee Push-Up)",
                    description = "Classic upper body exercise",
                    sets = 3,
                    reps = "8-10",
                    restSeconds = 60,
                    equipment = emptyList(),
                    muscleGroups = listOf("Chest", "Shoulders", "Triceps"),
                    instructions = listOf(
                        "Start in plank position",
                        "Lower body until chest nearly touches floor",
                        "Push back up to starting position",
                        "Beginners can perform on knees"
                    )
                ),
                Exercise(
                    id = "ex_plank",
                    name = "Plank Hold",
                    description = "Core stabilization exercise",
                    sets = 3,
                    reps = "20-30s",
                    restSeconds = 45,
                    equipment = emptyList(),
                    muscleGroups = listOf("Core", "Abs"),
                    instructions = listOf(
                        "Start in forearm plank position",
                        "Keep body in straight line",
                        "Hold position without sagging",
                        "Breathe normally"
                    )
                )
            )
        ),

        // FREE BASIC WORKOUT 2: Cardio Basic
        WorkoutPlan(
            id = "free_cardio_basic",
            name = "Cardio Basic",
            description = "Get your heart pumping with this simple cardio routine. No equipment needed, perfect for home workouts.",
            category = WorkoutCategory.FREE_BASIC,
            difficulty = ExperienceLevel.BEGINNER,
            durationMinutes = 20,
            caloriesBurnEstimate = 200,
            tags = listOf("Cardio", "Beginner", "Fat Burn"),
            exercises = listOf(
                Exercise(
                    id = "ex_jumping_jacks",
                    name = "Jumping Jacks",
                    description = "Classic cardio warmup",
                    sets = 3,
                    reps = "30s",
                    restSeconds = 30,
                    equipment = emptyList(),
                    muscleGroups = listOf("Full Body", "Cardio"),
                    instructions = listOf(
                        "Start with feet together, arms at sides",
                        "Jump while spreading legs and raising arms",
                        "Return to starting position",
                        "Maintain steady rhythm"
                    )
                ),
                Exercise(
                    id = "ex_high_knees",
                    name = "High Knees",
                    description = "Running in place with high knee lift",
                    sets = 3,
                    reps = "30s",
                    restSeconds = 30,
                    equipment = emptyList(),
                    muscleGroups = listOf("Legs", "Cardio"),
                    instructions = listOf(
                        "Run in place",
                        "Lift knees high toward chest",
                        "Pump arms naturally",
                        "Keep core engaged"
                    )
                ),
                Exercise(
                    id = "ex_mountain_climbers",
                    name = "Mountain Climbers",
                    description = "Dynamic full body cardio exercise",
                    sets = 3,
                    reps = "20s",
                    restSeconds = 40,
                    equipment = emptyList(),
                    muscleGroups = listOf("Core", "Cardio", "Full Body"),
                    instructions = listOf(
                        "Start in push-up position",
                        "Bring one knee toward chest",
                        "Quickly switch legs",
                        "Keep hips level throughout"
                    )
                )
            )
        ),

        // PREMIUM WORKOUTS (locked for guests)
        WorkoutPlan(
            id = "premium_strength_upper",
            name = "Upper Body Strength",
            description = "Build muscle in your chest, shoulders, and arms with this advanced routine.",
            category = WorkoutCategory.PREMIUM,
            difficulty = ExperienceLevel.INTERMEDIATE,
            durationMinutes = 45,
            caloriesBurnEstimate = 300,
            tags = listOf("Strength", "Upper Body", "Muscle Building"),
            exercises = emptyList() // Placeholder for premium content
        ),

        WorkoutPlan(
            id = "premium_hiit_advanced",
            name = "HIIT Advanced",
            description = "High-intensity interval training for maximum fat burn and conditioning.",
            category = WorkoutCategory.PREMIUM,
            difficulty = ExperienceLevel.ADVANCED,
            durationMinutes = 30,
            caloriesBurnEstimate = 400,
            tags = listOf("HIIT", "Advanced", "Fat Burn"),
            exercises = emptyList() // Placeholder for premium content
        ),

        WorkoutPlan(
            id = "premium_leg_day",
            name = "Leg Day Complete",
            description = "Comprehensive lower body workout targeting quads, hamstrings, and glutes.",
            category = WorkoutCategory.PREMIUM,
            difficulty = ExperienceLevel.INTERMEDIATE,
            durationMinutes = 50,
            caloriesBurnEstimate = 350,
            tags = listOf("Legs", "Strength", "Muscle Building"),
            exercises = emptyList() // Placeholder for premium content
        )
    )

    override fun getAllWorkouts(): Flow<List<WorkoutPlan>> {
        return flowOf(mockWorkouts)
    }

    override fun getWorkoutsByCategory(category: WorkoutCategory): Flow<List<WorkoutPlan>> {
        return flowOf(mockWorkouts.filter { it.category == category })
    }

    override suspend fun getWorkoutById(id: String): WorkoutPlan? {
        return mockWorkouts.firstOrNull { it.id == id }
    }
}
