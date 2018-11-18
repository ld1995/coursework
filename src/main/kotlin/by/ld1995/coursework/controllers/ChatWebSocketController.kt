package by.ld1995.coursework.controllers

import by.ld1995.coursework.dto.chat.ChatMessageRequest
import by.ld1995.coursework.dto.chat.ChatMessageResponse
import by.ld1995.coursework.dto.chat.ChatRequest
import by.ld1995.coursework.dto.chat.ChatResponse
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.exception.ChatNotFoundException
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
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller
import java.security.Principal
import java.time.Instant
import java.util.*

@Controller
class ChatWebSocketController(val chatRepository: ChatRepository,
                              val userRepository: UserRepository,
                              val chatMessageRepository: ChatMessageRepository,
                              val messagingTemplate: SimpMessageSendingOperations) {

    @MessageMapping("/chat/create")
    fun createChat(@Payload chat: ChatRequest, currentUser: Principal) {
        val user = this.userRepository.findByUsername(currentUser.name).orElseThrow { UserNotFoundException(currentUser) }
        val createAt = Instant.now()
        val participantIds = chat.participants.map(UserSummary::id).toMutableList()
        val participants = this.userRepository.findAllById(participantIds)
        val entityChat = this.chatRepository.save(
                Chat(chat.name, user, participants, createAt, chat.closeAt, Collections.emptySet()))
        this.chatMessageRepository.save(
                ChatMessage(user, createAt, "Сhat started!", entityChat))
        val chatResponse = ChatResponse(entityChat.id, entityChat.name, UserSummary(user.id, user.username, user.fullName),
                entityChat.createdAt, entityChat.closeAt)
        participantIds.add(user.id)
        participantIds.forEach { id -> messagingTemplate.convertAndSend("/topic/public/$id", chatResponse) }
    }

    @MessageMapping("/chat")
    fun sendMessage(@Payload chatMessage: ChatMessageRequest, currentUser: Principal) {
        val user = userRepository.findByUsername(currentUser.name).orElseThrow { UserNotFoundException(currentUser) }
        val chatId = chatMessage.chatId
        val chat = this.chatRepository.findById(chatId).orElseThrow { ChatNotFoundException(chatId) }
        this.chatMessageRepository.save(ChatMessage(user, Instant.now(), chatMessage.content, chat))
        val message = ChatMessageResponse(UserSummary(user.id, user.username, user.fullName),
                Instant.now(), chatMessage.content, chatId)
        messagingTemplate.convertAndSend("/topic/public/chat", message)
    }

//    //todo
//    @MessageMapping("/chat/{chatId}/disconnect")
//    @SendTo("/topic/public")
//    fun userDisconnect(@DestinationVariable chatId: Long,
//                       currentUser: Principal) {
////        val user = userRepository.findById(currentUser.getId()).orElseThrow { UserNotFoundException(currentUser) }
//        this.chatRepository.deleteById(chatId)
//    }
}