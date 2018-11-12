package by.ld1995.coursework.controllers

import by.ld1995.coursework.dto.chat.ChatMessageResponse
import by.ld1995.coursework.dto.chat.ChatResponse
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.exception.ChatNotFoundException
import by.ld1995.coursework.exception.UserNotFoundException
import by.ld1995.coursework.repositories.ChatMessageRepository
import by.ld1995.coursework.repositories.ChatRepository
import by.ld1995.coursework.repositories.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.stream.Collectors

@RestController
@RequestMapping("/api")
class ChatController(val chatRepository: ChatRepository,
                     val userRepository: UserRepository,
                     val chatMessageRepository: ChatMessageRepository) {

    @GetMapping("/chat")
    fun getChat(currentUser: Principal): List<ChatResponse> {
        val user = userRepository.findByUsername(currentUser.name).orElseThrow { UserNotFoundException(currentUser) }
        val chats = chatRepository.findByAuthorOrParticipantsMemberOf(user.id)
        return chats.stream().map { chat ->
            ChatResponse(chat.id, chat.name, UserSummary(chat.author.id, chat.author.username, chat.author.fullName),
                    chat.participants.stream().map { user -> user.username }.collect(Collectors.toList()),
                    chat.createdAt, chat.closeAt)
        }.collect(Collectors.toList())
    }

    @GetMapping("/message/{chatId}")
    fun getMessages(@PathVariable chatId: Long): List<ChatMessageResponse> {
        val messages = chatMessageRepository.findByChat(chatId).orElseThrow { ChatNotFoundException(chatId) }
        return messages.stream().map { message ->
            ChatMessageResponse(
                    UserSummary(message.author.id, message.author.username, message.author.fullName),
                    message.timeCreation, message.content, chatId.toString()
            )
        }.collect(Collectors.toList())
    }
}