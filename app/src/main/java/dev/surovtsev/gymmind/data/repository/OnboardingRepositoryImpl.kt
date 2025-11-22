package dev.surovtsev.gymmind.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.model.UserProfile
import dev.surovtsev.gymmind.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userPreferences: UserPreferences,
    private val gson: Gson
) : OnboardingRepository {

    override suspend fun saveOnboardingProgress(userProfile: UserProfile): Result<Unit> {
        return try {
            val json = gson.toJson(userProfile)
            userPreferences.setOnboardingProgress(json)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getOnboardingProgress(): Flow<UserProfile?> {
        return userPreferences.getOnboardingProgress().map { json ->
            try {
                json?.let { gson.fromJson(it, UserProfile::class.java) }
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun completeOnboarding(userProfile: UserProfile): Result<Unit> {
        return try {
            val currentUser = firebaseAuth.currentUser
                ?: return Result.failure(Exception("User not authenticated"))

            // Update profile with hasCompletedOnboarding = true
            val completedProfile = userProfile.copy(hasCompletedOnboarding = true)

            // Save to Firestore
            firestore.collection("users")
                .document(currentUser.uid)
                .set(completedProfile)
                .await()

            // Update DataStore flags
            userPreferences.setOnboardingCompleted(true)

            // Clear temporary progress
            userPreferences.clearOnboardingProgress()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearOnboardingProgress(): Result<Unit> {
        return try {
            userPreferences.clearOnboardingProgress()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
