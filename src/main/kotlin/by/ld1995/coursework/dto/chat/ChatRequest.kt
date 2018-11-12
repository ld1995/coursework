package by.ld1995.coursework.dto.chat

import java.time.Instant
import javax.validation.constraints.NotBlank

class ChatRequest {

    @NotBlank
    val name: String = ""

    val closeAt: Instant = Instant.MAX

//    val participants: Set<User> = HashSet()
}