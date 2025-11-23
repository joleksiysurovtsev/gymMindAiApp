package dev.surovtsev.gymmind.data.local.dao

import androidx.room.*
import dev.surovtsev.gymmind.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import java.time.Instant

/**
 * DAO для работы с пользователями
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserFlow(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("UPDATE users SET isSynced = 0 WHERE id = :userId")
    suspend fun markAsUnsynced(userId: String)

    @Query("SELECT * FROM users WHERE isSynced = 0")
    suspend fun getUnsyncedUsers(): List<UserEntity>

    @Transaction
    @Query("""
        UPDATE users
        SET currentStreak = :streak,
            longestStreak = CASE
                WHEN :streak > longestStreak THEN :streak
                ELSE longestStreak
            END,
            lastActiveAt = :now
        WHERE id = :userId
    """)
    suspend fun updateStreak(userId: String, streak: Int, now: Instant)

    @Query("""
        UPDATE users
        SET level = :level,
            experiencePoints = :xp
        WHERE id = :userId
    """)
    suspend fun updateLevelAndXp(userId: String, level: Int, xp: Int)
}
