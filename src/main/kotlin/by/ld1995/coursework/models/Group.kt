package by.ld1995.coursework.models

import by.ld1995.coursework.models.user.User
import javax.persistence.*

@Entity
@Table(name = "coursework_group")
data class Group(

        val name: String

//        @OneToOne(fetch = FetchType.LAZY)
//        val headman: User,
//
//        @OneToOne(fetch = FetchType.LAZY)
//        val curator: User,
//
//        @OneToMany(mappedBy = "group")
//        val users: Set<User> = HashSet()

) : PersistentObject()