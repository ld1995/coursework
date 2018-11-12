package by.ld1995.coursework.dto.chat

import java.time.Instant
import javax.validation.constraints.NotBlank

class ChatMessageRequest {

    val timeCreation: Instant = Instant.now()

    @NotBlank
    val content: String = ""

    @NotBlank
    val chatId: String = ""
}