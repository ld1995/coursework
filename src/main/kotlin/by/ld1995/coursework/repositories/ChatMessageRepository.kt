package by.ld1995.coursework.repositories

import by.ld1995.coursework.models.chat.ChatMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE m.chat.id = ?1")
    fun findByChat(chatId: Long): Optional<List<ChatMessage>>
}