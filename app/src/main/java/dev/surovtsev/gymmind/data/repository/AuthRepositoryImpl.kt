package dev.surovtsev.gymmind.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.model.UserProfile
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override suspend fun signInWithGoogle(idToken: String): Result<UserProfile> {
        return try {
            // Authenticate with Firebase
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user ?: return Result.failure(Exception("User is null"))

            // Check if user already exists in Firestore
            val userDoc = firestore.collection("users")
                .document(user.uid)
                .get()
                .await()

            val profile = if (userDoc.exists()) {
                // Existing user - load from Firestore
                userDoc.toObject(UserProfile::class.java)?.copy(id = user.uid)
                    ?: UserProfile(
                        id = user.uid,
                        email = user.email ?: "",
                        displayName = user.displayName ?: "",
                        photoUrl = user.photoUrl?.toString(),
                        hasCompletedOnboarding = false
                    )
            } else {
                // New user - create profile
                val newProfile = UserProfile(
                    id = user.uid,
                    email = user.email ?: "",
                    displayName = user.displayName ?: "",
                    photoUrl = user.photoUrl?.toString(),
                    hasCompletedOnboarding = false,
                    createdAt = System.currentTimeMillis()
                )

                // Save to Firestore
                firestore.collection("users")
                    .document(user.uid)
                    .set(newProfile)
                    .await()

                newProfile
            }

            // Save login state to DataStore
            userPreferences.setLoggedIn(true, user.uid)
            userPreferences.setOnboardingCompleted(profile.hasCompletedOnboarding)

            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
        userPreferences.clear()
    }

    override fun getCurrentUser(): Flow<UserProfile?> = flow {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            try {
                val doc = firestore.collection("users")
                    .document(currentUser.uid)
                    .get()
                    .await()

                val profile = doc.toObject(UserProfile::class.java)?.copy(id = currentUser.uid)
                emit(profile)
            } catch (e: Exception) {
                emit(null)
            }
        } else {
            emit(null)
        }
    }

    override suspend fun saveOnboardingCompleted(userId: String) {
        try {
            firestore.collection("users")
                .document(userId)
                .update("hasCompletedOnboarding", true)
                .await()

            userPreferences.setOnboardingCompleted(true)
        } catch (e: Exception) {
            // Handle error
        }
    }
}
