package dev.surovtsev.gymmind.domain.repository

import dev.surovtsev.gymmind.domain.model.WorkoutCategory
import dev.surovtsev.gymmind.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    /**
     * Get all available workouts
     */
    fun getAllWorkouts(): Flow<List<WorkoutPlan>>

    /**
     * Get workouts filtered by category
     */
    fun getWorkoutsByCategory(category: WorkoutCategory): Flow<List<WorkoutPlan>>

    /**
     * Get a specific workout by ID
     */
    suspend fun getWorkoutById(id: String): WorkoutPlan?
}
