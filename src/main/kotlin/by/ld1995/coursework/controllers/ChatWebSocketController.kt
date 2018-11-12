package by.ld1995.coursework.controllers

import by.ld1995.coursework.dto.chat.ChatMessageRequest
import by.ld1995.coursework.dto.chat.ChatMessageResponse
import by.ld1995.coursework.dto.chat.ChatRequest
import by.ld1995.coursework.dto.chat.ChatResponse
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.exception.UserNotFoundException
import by.ld1995.coursework.models.chat.Chat
import by.ld1995.coursework.models.chat.ChatMessage
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.repositories.ChatMessageRepository
import by.ld1995.coursework.repositories.ChatRepository
import by.ld1995.coursework.repositories.UserRepository
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.security.Principal
import java.time.Instant
import java.util.*
import java.util.stream.Collectors

@Controller
class ChatWebSocketController(val chatRepository: ChatRepository,
                              val userRepository: UserRepository,
                              val chatMessageRepository: ChatMessageRepository) {

    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(@DestinationVariable chatId: Long,
                    @Payload chatMessage: ChatMessageRequest,
                    currentUser: Principal): ChatMessageResponse {
        val user = userRepository.findByUsername(currentUser.name).orElseThrow { UserNotFoundException(currentUser) }
        chatRepository.findById(chatId).ifPresent { chat ->
            chatMessageRepository.save(ChatMessage(user, chatMessage.timeCreation, chatMessage.content, chat))
        }
        return ChatMessageResponse(UserSummary(user.id, user.username, user.fullName),
                chatMessage.timeCreation, chatMessage.content, chatMessage.chatId)
    }

    @MessageMapping("/chat/create")
    @SendTo("/topic/public")
    fun createChat(@Payload chat: ChatRequest, currentUser: Principal): ChatResponse {
        val user = userRepository.findByUsername(currentUser.name).orElseThrow { UserNotFoundException(currentUser) }
        val createAt = Instant.now()
        val entityChat = chatRepository.save(
                Chat(chat.name, user, Collections.emptySet(), createAt, chat.closeAt, Collections.emptySet()))
        val entityMessage = chatMessageRepository.save(
                ChatMessage(user, createAt, "Сhat started!", entityChat))
        return ChatResponse(entityChat.id, entityChat.name, UserSummary(user.id, user.username, user.fullName),
                entityChat.participants.stream().map(User::username).collect(Collectors.toList()),
                createAt, entityChat.closeAt)
        //Collections.singleton(
        //                ChatMessageResponse(entityMessage.author.fullName, createAt, entityMessage.content, entityChat.id.toString())
    }
    //создать чат и записать в него приветственное сообщение, оповестить всех участников

    //todo
    @MessageMapping("/chat/{chatId}/disconnect")
    @SendTo("/topic/public")
    fun userDisconnect(@DestinationVariable chatId: Long,
                       currentUser: Principal) {
//        val user = userRepository.findById(currentUser.getId()).orElseThrow { UserNotFoundException(currentUser) }
        chatRepository.deleteById(chatId)
    }
}