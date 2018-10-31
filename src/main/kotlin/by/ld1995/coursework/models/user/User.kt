package by.ld1995.coursework.models.user

import by.ld1995.coursework.models.Chat
import by.ld1995.coursework.models.PersistentObject
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "coursework_user",
        uniqueConstraints = [UniqueConstraint(columnNames = ["username", "email"])])
data class User(

        @Size(min = 3, max = 65)
        val username: String,

        @Size(min = 3, max = 255)
        val fullName: String,

        @Size(min = 11, max = 12)
        val phoneNumber: String,

        @get: NotBlank
        val email: String,

        val password: String,

        val active: Int,

        @ManyToOne
        val group: Group,

        @ManyToMany
        val chats: Set<Chat> = HashSet(),

        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(name = "coursework_user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        val roles: Set<Role> = HashSet()

) : PersistentObject()
