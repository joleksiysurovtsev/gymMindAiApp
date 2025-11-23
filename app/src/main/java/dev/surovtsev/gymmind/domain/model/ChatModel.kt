package dev.surovtsev.gymmind.domain.model

import java.time.Instant

/**
 * Chat Message Model
 */
data class ChatMessage(
    val id: String,
    val content: String,
    val sender: MessageSender,
    val timestamp: Instant = Instant.now(),
    val isTyping: Boolean = false
)

enum class MessageSender {
    USER,
    AI_COACH
}

/**
 * Chat Session Model
 */
data class ChatSession(
    val id: String,
    val messages: List<ChatMessage> = emptyList(),
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)
