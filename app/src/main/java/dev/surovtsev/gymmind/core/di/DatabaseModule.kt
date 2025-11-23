package dev.surovtsev.gymmind.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.surovtsev.gymmind.data.local.dao.*
import dev.surovtsev.gymmind.data.local.database.FitnessDatabase
import javax.inject.Singleton

/**
 * Hilt module для предоставления Room Database и DAOs
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFitnessDatabase(
        @ApplicationContext context: Context
    ): FitnessDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FitnessDatabase::class.java,
            FitnessDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // TODO: Replace with proper migrations in production
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: FitnessDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(database: FitnessDatabase): ExerciseDao {
        return database.exerciseDao()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(database: FitnessDatabase): WorkoutDao {
        return database.workoutDao()
    }

    @Provides
    @Singleton
    fun provideProgramDao(database: FitnessDatabase): ProgramDao {
        return database.programDao()
    }
}
