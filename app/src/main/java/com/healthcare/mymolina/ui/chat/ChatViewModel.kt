package com.healthcare.mymolina.ui.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Message(val text: String, val isUser: Boolean)


class ChatViewModel : ViewModel() {
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages

    private var debounceJob: Job? = null
    private val triggerWords = listOf(
        "done", "great", "fine", "ok", "okay", "perfect", "good", "resolved", "understood", "clear"
    )

    fun sendMessage(message: String) {
        _messages.add(Message(message, true))
        debounceResponse(message)
    }

    private fun debounceResponse(userMessage: String) {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(6000) // Wait for 6 seconds of inactivity


            if (triggerWords.any { userMessage.contains(it, ignoreCase = true) }) {
                _messages.add(
                    Message(
                        "Thank you for your message! If you have any more questions or need further assistance, feel free to ask.",
                        false
                    )
                )
            } else{
                _messages.add(
                    Message(
                        "Our team will respond within 24 hours. Make sure you write your full name, phone number, and email address. If you are done, write done.",
                        false
                    )
                )
            }

        }
    }
}
