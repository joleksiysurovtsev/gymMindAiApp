package dev.surovtsev.gymmind.domain.model.enums

/**
 * User-related enums
 */
enum class Gender {
    MALE,
    FEMALE,
    OTHER,
    PREFER_NOT_TO_SAY
}

enum class ActivityLevel {
    SEDENTARY,          // Малоподвижный
    LIGHTLY_ACTIVE,     // Легкая активность
    MODERATELY_ACTIVE,  // Умеренная активность
    VERY_ACTIVE,        // Высокая активность
    EXTREMELY_ACTIVE    // Очень высокая активность
}

enum class PrivacyLevel {
    PUBLIC,                // Публичный
    FRIENDS_ONLY,          // Только друзья
    PRIVATE,               // Приватный
    CUSTOM                 // Настраиваемый
}
