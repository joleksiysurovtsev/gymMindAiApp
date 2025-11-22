package dev.surovtsev.gymmind.domain.repository

import dev.surovtsev.gymmind.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<UserProfile>
    suspend fun signOut()
    fun getCurrentUser(): Flow<UserProfile?>
    suspend fun saveOnboardingCompleted(userId: String)
}
