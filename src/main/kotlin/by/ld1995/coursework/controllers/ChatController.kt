package by.ld1995.coursework.controllers

import by.ld1995.coursework.dto.chat.ChatMessageResponse
import by.ld1995.coursework.dto.chat.ChatResponse
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.models.chat.ChatMessage
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.services.ChatService
import by.ld1995.coursework.services.MessageService
import by.ld1995.coursework.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api")
class ChatController(private val chatService: ChatService,
                     private val userService: UserService,
                     private val messageService: MessageService) {

    @GetMapping("/chat")
    fun getChat(currentUser: Principal): List<ChatResponse> {
        val user: User = userService.findUserByUsername(currentUser.name)
        return chatService.findByAuthorOrParticipantsMemberOf(user.id)
    }

    @GetMapping("/message/{chatId}")
    fun getMessages(@PathVariable chatId: Long): List<ChatMessageResponse> =
        messageService.findMessagesByChatId(chatId)
}