package by.ld1995.coursework.controllers

import by.ld1995.coursework.dto.chat.ChatMessageRequest
import by.ld1995.coursework.dto.chat.ChatMessageResponse
import by.ld1995.coursework.dto.chat.ChatRequest
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.models.chat.Chat
import by.ld1995.coursework.models.chat.ChatMessage
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.services.ChatService
import by.ld1995.coursework.services.MessageService
import by.ld1995.coursework.services.UserService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller
import java.security.Principal
import java.time.Instant
import java.util.*

@Controller
class ChatWebSocketController(private val chatService: ChatService,
                              private val userService: UserService,
                              private val messageService: MessageService,
                              private val messagingTemplate: SimpMessageSendingOperations) {

    @MessageMapping("/chat/create")
    fun createChat(@Payload chat: ChatRequest, currentUser: Principal) {
        val user: User = this.userService.findUserByUsername(currentUser.name)
        val createAt: Instant = Instant.now()
        val participantIds: MutableList<Long> = chat.participants.map(UserSummary::id).toMutableList()
        val participants: MutableList<User> = this.userService.findAllById(participantIds)
        val entityChat: Chat = this.chatService.save(
                Chat(chat.name, user, participants, createAt, chat.closeAt, Collections.emptySet()))
        this.messageService.save(ChatMessage(user, createAt, "Chat started!", entityChat))
        val chatResponse = this.chatService.convertChatToChatResponse(entityChat, user)
        participantIds.add(user.id)
        participantIds.forEach { id -> messagingTemplate.convertAndSend("/topic/public/$id", chatResponse) }
    }

    @MessageMapping("/chat")
    fun sendMessage(@Payload chatMessage: ChatMessageRequest, currentUser: Principal) {
        val user: User = userService.findUserByUsername(currentUser.name)
        val chatId: Long = chatMessage.chatId
        val chat: Chat = this.chatService.findChatById(chatId)
        val saveMessage: ChatMessage = this.messageService.save(ChatMessage(user, Instant.now(), chatMessage.content, chat))
        val message: ChatMessageResponse = this.messageService.convertMessageToMessageResponse(saveMessage, user)
        messagingTemplate.convertAndSend("/topic/public/chat/$chatId", message)
    }
}