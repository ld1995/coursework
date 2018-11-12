package by.ld1995.coursework.models.chat

import by.ld1995.coursework.models.PersistentObject
import by.ld1995.coursework.models.user.User
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "coursework_chat_message")
data class ChatMessage(

        @OneToOne(fetch = FetchType.LAZY)
        val author: User,

        val timeCreation: Instant,

        @Lob
        val content: String,

        @ManyToOne
        val chat: Chat

) : PersistentObject()