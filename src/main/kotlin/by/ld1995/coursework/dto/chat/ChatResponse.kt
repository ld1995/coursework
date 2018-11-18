package by.ld1995.coursework.dto.chat

import by.ld1995.coursework.dto.user.UserSummary
import java.time.Instant

//Todo participants: Set<UserProfile>
data class ChatResponse(val id: Long, val name: String, val author: UserSummary, val createdAt: Instant,
                        val closeAt: Instant)