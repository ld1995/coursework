package by.ld1995.coursework.models

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "coursework_user")
data class User(

        @Size(min = 3, max = 65)
        val username: String,

        @Size(min = 3, max = 255)
        val fullName: String,

        @Size(min = 11, max = 12)
        val phoneNumber: String,

        val email: String,

        @ManyToOne
        val group: Group

) : PersistentObject()
