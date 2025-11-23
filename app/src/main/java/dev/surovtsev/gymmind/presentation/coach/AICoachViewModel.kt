package dev.surovtsev.gymmind.presentation.coach

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.domain.model.ChatMessage
import dev.surovtsev.gymmind.domain.repository.ChatRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AICoachViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    val messages: StateFlow<List<ChatMessage>> = chatRepository.getMessages()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun sendMessage(message: String) {
        if (message.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            chatRepository.sendMessage(message)
                .onSuccess {
                    Log.d("AICoachViewModel", "Message sent successfully")
                }
                .onFailure { error ->
                    Log.e("AICoachViewModel", "Failed to send message", error)
                    _errorMessage.value = "Не удалось отправить сообщение"
                }

            _isLoading.value = false
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun clearHistory() {
        viewModelScope.launch {
            chatRepository.clearHistory()
        }
    }
}
