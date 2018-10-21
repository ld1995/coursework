package by.ld1995.coursework.models

import javax.persistence.*

@Entity
@Table(name = "coursework_group")
data class Group(

        val number: String,

        @OneToOne(fetch = FetchType.LAZY)
        val headman: User,

        @OneToOne(fetch = FetchType.LAZY)
        val curator: User,

        @OneToMany(mappedBy = "group")
        //@OneToMany(mappedBy = "workbook", cascade = CascadeType.ALL, orphanRemoval = true)
        val users: Set<User> = HashSet()

) : PersistentObject()