package by.ld1995.coursework.dto.chat

import javax.validation.constraints.NotBlank

data class ChatMessageRequest (@NotBlank val content: String, @NotBlank val chatId: Long)