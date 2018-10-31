package by.ld1995.coursework.models

import by.ld1995.coursework.models.user.User
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "coursework_chat")
data class Chat (

        @OneToOne(fetch = FetchType.LAZY)
        val author: User,

        @ManyToMany(cascade = [CascadeType.ALL])
            @JoinTable(
                    name = "coursework_user_chat",
                    joinColumns = [JoinColumn(name = "chat_id")],
                    inverseJoinColumns = [JoinColumn(name = "user_id")]
            )
        val participants: Set<User> = HashSet(),

        val name: String,

        val createAt: Date,

        val closeAt: Date
) : PersistentObject()