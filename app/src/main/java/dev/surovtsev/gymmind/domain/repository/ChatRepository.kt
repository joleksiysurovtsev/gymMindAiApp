package dev.surovtsev.gymmind.domain.repository

import dev.surovtsev.gymmind.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    /**
     * Отправить сообщение AI тренеру
     */
    suspend fun sendMessage(message: String): Result<ChatMessage>

    /**
     * Получить историю сообщений
     */
    fun getMessages(): Flow<List<ChatMessage>>

    /**
     * Очистить историю чата
     */
    suspend fun clearHistory()
}
