package by.ld1995.coursework.repositories

import by.ld1995.coursework.models.chat.Chat
import by.ld1995.coursework.models.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository : JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE c.author.id = ?1 OR ?1 MEMBER OF c.participants")
    fun findByAuthorOrParticipantsMemberOf(userId: Long): List<Chat>
}