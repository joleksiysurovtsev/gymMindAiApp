package dev.surovtsev.gymmind.data.local.dao

import androidx.room.*
import dev.surovtsev.gymmind.data.local.entity.*
import kotlinx.coroutines.flow.Flow
import java.time.Instant

/**
 * DAO для работы с программами тренировок
 */
@Dao
interface ProgramDao {
    @Query("SELECT * FROM programs WHERE id = :programId")
    suspend fun getProgramById(programId: String): ProgramEntity?

    @Transaction
    @Query("SELECT * FROM programs WHERE id = :programId")
    suspend fun getProgramWithWorkouts(programId: String): ProgramWithWorkouts?

    @Query("""
        SELECT * FROM programs
        WHERE isPublic = 1 AND isVerified = 1
        ORDER BY
            CASE WHEN isFeatured = 1 THEN 0 ELSE 1 END,
            createdAt DESC
        LIMIT :limit
    """)
    fun getFeaturedPrograms(limit: Int = 20): Flow<List<ProgramEntity>>

    @Query("""
        SELECT * FROM programs
        WHERE type = :type AND isPublic = 1
        ORDER BY createdAt DESC
        LIMIT :limit
    """)
    fun getProgramsByType(type: String, limit: Int = 50): Flow<List<ProgramEntity>>

    @Query("""
        SELECT * FROM programs
        WHERE goals LIKE '%' || :goal || '%' AND isPublic = 1
        ORDER BY createdAt DESC
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

    @Update
    suspend fun updateProgram(program: ProgramEntity)

    @Delete
    suspend fun deleteProgram(program: ProgramEntity)

    @Query("UPDATE programs SET isSynced = 0 WHERE id = :programId")
    suspend fun markAsUnsynced(programId: String)

    @Query("SELECT * FROM programs WHERE isSynced = 0")
    suspend fun getUnsyncedPrograms(): List<ProgramEntity>
}

/**
 * Data class для получения программы с тренировками
 */
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
