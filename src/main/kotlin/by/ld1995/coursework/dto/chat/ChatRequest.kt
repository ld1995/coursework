package by.ld1995.coursework.dto.chat

import by.ld1995.coursework.dto.user.UserSummary
import java.time.Instant
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class ChatRequest {

    @NotBlank
    @Size(min = 4, max = 40)
    val name: String = ""

    val closeAt: Instant = Instant.MAX

    @NotBlank
    val participants: MutableList<UserSummary> = ArrayList()
}