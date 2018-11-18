package by.ld1995.coursework.models.chat

import by.ld1995.coursework.models.PersistentObject
import by.ld1995.coursework.models.user.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "coursework_chat")
data class Chat(

        val name: String,

        @OneToOne(fetch = FetchType.LAZY)
        val author: User,

        @ManyToMany(fetch = FetchType.LAZY)//CascadeType.PERSIST, CascadeType.MERGE
        @JoinTable(
                name = "coursework_user_chat",
                joinColumns = [JoinColumn(name = "chat_id")],
                inverseJoinColumns = [JoinColumn(name = "user_id")]
        )
        val participants: List<User> = ArrayList(),

        val createdAt: Instant,

        val closeAt: Instant,

        @OneToMany(mappedBy = "chat")
        val messages: Set<ChatMessage> = HashSet()

) : PersistentObject()