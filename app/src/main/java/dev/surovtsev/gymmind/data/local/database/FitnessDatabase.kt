package dev.surovtsev.gymmind.data.local.database

import androidx.room.*
import dev.surovtsev.gymmind.data.local.converters.InstantConverter
import dev.surovtsev.gymmind.data.local.converters.LocalDateConverter
import dev.surovtsev.gymmind.data.local.dao.*
import dev.surovtsev.gymmind.data.local.entity.*

/**
 * Room Database для приложения GymMind
 */
@Database(
    entities = [
        UserEntity::class,
        ExerciseEntity::class,
        ExerciseFts::class,
        WorkoutEntity::class,
        ProgramEntity::class,
        WorkoutExerciseCrossRef::class,
        ProgramWorkoutCrossRef::class,
        UserProgramEnrollment::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    InstantConverter::class,
    LocalDateConverter::class
)
abstract class FitnessDatabase : RoomDatabase() {

    // DAOs
    abstract fun userDao(): UserDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun programDao(): ProgramDao

    companion object {
        const val DATABASE_NAME = "fitness_database"
    }
}
