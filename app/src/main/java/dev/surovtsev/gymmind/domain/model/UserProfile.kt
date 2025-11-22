package dev.surovtsev.gymmind.domain.model

data class UserProfile(
    // Authentication & Basic Info
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String? = null,
    val hasCompletedOnboarding: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),

    // Onboarding: Basic Information (Card 1)
    val gender: Gender? = null,
    val age: Int? = null,
    val height: Int? = null,                    // cm
    val currentWeight: Float? = null,           // kg

    // Onboarding: Fitness Goal (Card 2)
    val goal: FitnessGoal? = null,

    // Onboarding: Target Weight (Card 3 - conditional)
    val targetWeight: Float? = null,            // kg

    // Onboarding: Experience Level (Card 4)
    val experienceLevel: ExperienceLevel? = null,

    // Onboarding: Workout Location (Card 5)
    val location: WorkoutLocation? = null,

    // Onboarding: Equipment (Card 6 - conditional)
    val equipment: List<Equipment> = emptyList(),

    // Onboarding: Schedule (Card 7)
    val daysPerWeek: DaysPerWeek? = null,
    val minutesPerWorkout: MinutesPerWorkout? = null,

    // Onboarding: Health Limitations (Card 8)
    val limitations: List<HealthLimitation> = emptyList(),
    val customLimitation: String? = null,       // For "OTHER" limitation

    // Onboarding: Basic Skills (Card 9 - conditional for beginners)
    val canDoSquats: Boolean? = null,
    val canDoPushUps: Boolean? = null,
    val canDoPlank: Boolean? = null,

    // Onboarding: Notifications (Card 10)
    val notificationTime: NotificationTime? = null
)
