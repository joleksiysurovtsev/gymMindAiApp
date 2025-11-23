package dev.surovtsev.gymmind.data.local.dao

import androidx.room.*
import dev.surovtsev.gymmind.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с упражнениями
 */
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
        JOIN exercise_fts ON exercises.rowid = exercise_fts.rowid
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
