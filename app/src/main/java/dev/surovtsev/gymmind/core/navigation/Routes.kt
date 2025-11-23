package dev.surovtsev.gymmind.core.navigation

object Routes {
    const val SPLASH = "splash"
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val MAIN = "main"
    const val HOME = "home"
    const val PROGRAMS = "programs"
    const val WORKOUT_EXECUTION = "workout_execution/{workoutId}"
    const val AI_COACH = "ai_coach"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"

    fun workoutExecution(workoutId: String) = "workout_execution/$workoutId"
}
