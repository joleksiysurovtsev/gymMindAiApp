package dev.surovtsev.gymmind.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.surovtsev.gymmind.data.repository.AuthRepositoryImpl
import dev.surovtsev.gymmind.data.repository.OnboardingRepositoryImpl
import dev.surovtsev.gymmind.data.repository.WorkoutRepositoryImpl
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import dev.surovtsev.gymmind.domain.repository.OnboardingRepository
import dev.surovtsev.gymmind.domain.repository.WorkoutRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(impl: WorkoutRepositoryImpl): WorkoutRepository
}
