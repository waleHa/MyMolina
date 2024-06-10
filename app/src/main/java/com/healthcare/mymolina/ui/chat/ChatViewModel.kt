package com.healthcare.mymolina.ui.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Message(val text: String, val isUser: Boolean)

class ChatViewModel : ViewModel() {
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages

    fun sendMessage(message: String) {
        _messages.add(Message(message, true))
        viewModelScope.launch {
            delay(1000)
            _messages.add(Message("Our team will respond within 4 hours", false))
        }
    }
}