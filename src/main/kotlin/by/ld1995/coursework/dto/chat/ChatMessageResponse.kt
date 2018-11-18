package by.ld1995.coursework.dto.chat

import by.ld1995.coursework.dto.user.UserSummary
import java.time.Instant

data class ChatMessageResponse(val author: UserSummary, val timeCreation: Instant, val content: String, val chatId: Long)