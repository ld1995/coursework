package by.ld1995.coursework.services

import by.ld1995.coursework.dto.chat.ChatResponse
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.exception.ChatNotFoundException
import by.ld1995.coursework.models.chat.Chat
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.repositories.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatService(private val chatRepository: ChatRepository) {

    fun save(chat: Chat): Chat = chatRepository.save(chat)

    fun findChatById(id: Long): Chat =
            chatRepository.findById(id).orElseThrow { ChatNotFoundException(id) }

    fun findByAuthorOrParticipantsMemberOf(id: Long): List<ChatResponse> =
            chatRepository.findByAuthorOrParticipantsMemberOf(id).map { chat: Chat ->
                ChatResponse(chat.id, chat.name, UserSummary(chat.author.id, chat.author.username, chat.author.fullName),
                        chat.createdAt, chat.closeAt)
            }.toList()

    fun convertChatToChatResponse(chat: Chat, user: User): ChatResponse {
        return ChatResponse(chat.id, chat.name, UserSummary(user.id, user.username, user.fullName),
                chat.createdAt, chat.closeAt)
    }
}