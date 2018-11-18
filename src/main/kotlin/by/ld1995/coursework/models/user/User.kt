package by.ld1995.coursework.models.user

import by.ld1995.coursework.models.PersistentObject
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "coursework_user",
        uniqueConstraints = [UniqueConstraint(columnNames = ["username", "email", "phoneNumber"])])
data class User(

        val username: String,

        val fullName: String,

        val phoneNumber: String,

        val email: String,

        val password: String,

        val active: Int,

//        @ManyToOne
//        val group: Group,
//
//        @ManyToMany
//        val chats: Set<Chat> = HashSet(),

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "coursework_user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        val roles: Set<Role> = HashSet()

) : PersistentObject()
