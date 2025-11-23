package dev.surovtsev.gymmind.domain.repository

import dev.surovtsev.gymmind.domain.model.ProgramModel
import dev.surovtsev.gymmind.domain.model.enums.ProgramDifficulty
import dev.surovtsev.gymmind.domain.model.enums.ProgramGoal
import dev.surovtsev.gymmind.domain.model.enums.ProgramType
import kotlinx.coroutines.flow.Flow

interface ProgramRepository {
    /**
     * Получить все доступные программы
     */
    fun getAllPrograms(): Flow<List<ProgramModel>>

    /**
     * Получить программу по ID
     */
    suspend fun getProgramById(programId: String): ProgramModel?

    /**
     * Получить программы по типу
     */
    fun getProgramsByType(type: ProgramType): Flow<List<ProgramModel>>

    /**
     * Получить программы по цели
     */
    fun getProgramsByGoal(goal: ProgramGoal): Flow<List<ProgramModel>>

    /**
     * Получить программы по сложности
     */
    fun getProgramsByDifficulty(difficulty: ProgramDifficulty): Flow<List<ProgramModel>>

    /**
     * Получить только бесплатные программы
     */
    fun getFreePrograms(): Flow<List<ProgramModel>>

    /**
     * Получить только премиум программы
     */
    fun getPremiumPrograms(): Flow<List<ProgramModel>>

    /**
     * Получить избранные программы
     */
    fun getFeaturedPrograms(): Flow<List<ProgramModel>>
}
