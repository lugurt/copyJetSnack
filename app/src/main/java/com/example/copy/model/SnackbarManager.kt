package com.example.copy.model

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*

data class Message(val id: Long, @StringRes val messageId: Int)
object SnackbarManager {
  private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
  val message: StateFlow<List<Message>> get() = _messages.asStateFlow()

  fun showMessage(messageId: Int) {
    _messages.update {
      it + Message(UUID.randomUUID().mostSignificantBits, messageId = messageId)
    }
  }

  fun setMessageShown(messageId: Int) {
    _messages.update {
      it.filterNot { message -> message.messageId == messageId }
    }
  }
}