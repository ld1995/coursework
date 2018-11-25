package by.ld1995.coursework.services

import by.ld1995.coursework.dto.chat.ChatMessageResponse
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.models.chat.ChatMessage
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.repositories.ChatMessageRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class MessageService(private val messageRepository: ChatMessageRepository) {

    fun findMessagesByChatId(id: Long): List<ChatMessageResponse> =
            messageRepository.findByChat(id).map { message: ChatMessage ->
                ChatMessageResponse(
                        UserSummary(message.author.id, message.author.username, message.author.fullName),
                        message.timeCreation, message.content, id)
            }.toList()

    fun save(message: ChatMessage): ChatMessage = messageRepository.save(message)

    fun convertMessageToMessageResponse(message: ChatMessage, user: User): ChatMessageResponse {
        return ChatMessageResponse(UserSummary(user.id, user.username, user.fullName),
                Instant.now(), message.content, message.chat.id)
    }
}