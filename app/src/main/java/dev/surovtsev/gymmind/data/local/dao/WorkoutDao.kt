package dev.surovtsev.gymmind.data.local.dao

import androidx.room.*
import dev.surovtsev.gymmind.data.local.entity.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с тренировками
 */
@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: String): WorkoutEntity?

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

    @Query("UPDATE workouts SET isSynced = 0 WHERE id = :workoutId")
    suspend fun markAsUnsynced(workoutId: String)

    @Query("SELECT * FROM workouts WHERE isSynced = 0")
    suspend fun getUnsyncedWorkouts(): List<WorkoutEntity>
}

/**
 * Data class для получения тренировки с упражнениями
 */
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
