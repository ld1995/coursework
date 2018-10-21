package by.ld1995.coursework.models

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class PersistentObject (
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     val id: Long = 0
)