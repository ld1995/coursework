package by.ld1995.coursework.models.user

import by.ld1995.coursework.models.PersistentObject
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "coursework_role")
data class Role(

        val name: String

) : PersistentObject()